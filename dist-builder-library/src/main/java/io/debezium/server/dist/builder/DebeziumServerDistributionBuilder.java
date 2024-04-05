package io.debezium.server.dist.builder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.utils.ZipUtils;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import static java.util.Objects.nonNull;


//TODO:
// Figure problem with maven home automatic detection?


public class DebeziumServerDistributionBuilder {
    private static final String SERVER_FOLDER = "debezium-server-dist";
    private static final Logger LOGGER = LoggerFactory.getLogger(DebeziumServerDistributionBuilder.class);
    private static final String POM_TEMPLATE = "template.xslt";
    private final String VERSION = "2.5.1.Final";
    private List<Dependency> dependencyList;
    private CustomDebeziumServer customDebeziumServer;
    private String git_repo = null;
    private String pathToProject = null;
    private String pathToTargetPom;
    private Git distRepo;
    private Document pom;
    private Node dependenciesNode;

    public DebeziumServerDistributionBuilder() {
    }

    public DebeziumServerDistributionBuilder withDebeziumServer(CustomDebeziumServer server) {
        this.customDebeziumServer = server;
        return this;
    }

    public DebeziumServerDistributionBuilder withLocalProject(String path) {
        this.pathToProject = path;
        this.pathToTargetPom = String.format("%s/pom.xml", path);
        return this;
    }

    public DebeziumServerDistributionBuilder withRemoteRepository(String remoteRepository, String path) {
        this.pathToProject = path;
        this.git_repo = remoteRepository;
        return this;
    }

    public DebeziumServerDistributionBuilder withDefaultRepository(String path) {
        this.git_repo = "https://github.com/debezium/debezium-server.git";
        this.pathToProject = path;
        return this;
    }

    private void preparePomDocument() {
        DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("base-pom.xml")) {
            DocumentBuilder docBuilder = docFac.newDocumentBuilder();
            pom = docBuilder.parse(is);

            Node parent = pom.getElementsByTagName("parent").item(0);

            Node node = pom.createElement("version");
            if (nonNull(customDebeziumServer.getVersion())) {
                node.setTextContent(customDebeziumServer.getVersion());
            } else {
                node.setTextContent(VERSION);
            }
            parent.appendChild(node);

            NodeList dependencyNodes = pom.getElementsByTagName("dependencies");
            if (dependencyNodes.getLength() != 1) {
                LOGGER.error("[ERROR] Base pom file from resources is corrupted!");
                throw new RuntimeException("Base pom file from resources is corrupted!");
            }
            dependenciesNode = dependencyNodes.item(0);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(String pathToPom) {
        try (FileOutputStream output = new FileOutputStream(pathToPom)) {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(
                    new StreamSource(this.getClass().getClassLoader().getResourceAsStream(POM_TEMPLATE)));

            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
            DOMSource source = new DOMSource(pom);
            StreamResult result = new StreamResult(output);

            transformer.transform(source, result);

            copyFile("Dockerfile", true);
            copyFile("Dockerfile.insecure", true);
            copyFile("Readme.md", false);
        } catch (IOException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private void copyFile(String dockerfileName, boolean dockerfile) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(dockerfileName);
        if (nonNull(is)) {
            String dockerfileString = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            if (dockerfile) {
                if (nonNull(customDebeziumServer.getVersion())) {
                    dockerfileString = dockerfileString.replace("$dbz_version", customDebeziumServer.getVersion());
                } else {
                    dockerfileString = dockerfileString.replace("$dbz_version", VERSION);
                }
            }
            File f = new File(String.format("%s/" + dockerfileName, pathToProject));
            if (!f.exists()) {
                f.createNewFile();
            }
            try (FileOutputStream fs = new FileOutputStream(f)) {
                fs.write(dockerfileString.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void cloneDistFolder() {
        try {
            this.distRepo = Git.cloneRepository().setDepth(1).setURI(git_repo).setDirectory(new File(this.pathToProject)).setNoCheckout(true).call();
            this.distRepo.checkout().setName("main").setStartPoint("origin/main").addPath(SERVER_FOLDER).call();
            this.distRepo.getRepository().close();
            this.pathToTargetPom = String.format("%s/%s/pom.xml", pathToProject, SERVER_FOLDER);
            this.pathToProject = pathToProject + "/" + SERVER_FOLDER;
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeCurrentPom() {
        File currentPom = new File(pathToTargetPom);
        currentPom.delete();
    }

    public DebeziumServerDistributionBuilder build() {
        this.dependencyList = new ArrayList<>();
        if (git_repo != null) {
            LOGGER.trace("Cloning the remote repository");
            cloneDistFolder();
            LOGGER.trace("Cloning done");
        }
        LOGGER.trace("Parsing pom document template");
        preparePomDocument();
        LOGGER.trace("Started build of pom file");
        removeCurrentPom();
        addDependency(customDebeziumServer.getSinkNode().buildNode(pom, dependencyList));
        addDependency(customDebeziumServer.getSourceNode().buildNode(pom, dependencyList));
        addDependency(customDebeziumServer.getInternalSchemaHistory().buildNode(pom, dependencyList));
        addDependency(customDebeziumServer.getOffsetStorage().buildNode(pom, dependencyList));

        for (Dependency dependency : customDebeziumServer.getDependencyList()) {
            addDependency(dependency.buildNode(pom, dependencyList));
        }
        LOGGER.trace("Finished building pom file");
        write(pathToTargetPom);
        return this;
    }

    private void addDependency(Node dep) {
        if (dep != null) {
            dependenciesNode.appendChild(dep);
        }
    }

    public void mavenBuild(String mavenHome) {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File(pathToTargetPom));
        request.setGoals(Collections.singletonList("package"));
        request.setProfiles(Collections.singletonList("assembly"));
        Properties props = new Properties();
        props.put("maven.test.skip", "");
        request.setProperties(props);
        Invoker invoker = new DefaultInvoker();
        // TODO: More likely set this to grep env var MAVEN_HOME or property
        invoker.setMavenHome(new File(mavenHome));
        try {
            invoker.execute(request);
        } catch (MavenInvocationException e) {
            throw new RuntimeException(e);
        }
    }

    public DebeziumServerDistributionBuilder generateConfigurationProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        propertiesBuilder.putAll(customDebeziumServer);
        String pathToPropertiesFile = String.format("%s/src/main/resources/distro/conf/application.properties", pathToProject);
        try {
            String properties = ConfigurationSerializer.configToPropertiesFile(customDebeziumServer);
            FileOutputStream outputStream = new FileOutputStream(pathToPropertiesFile);
            outputStream.write(properties.getBytes());
            outputStream.close();
        } catch (IOException e) {
            LOGGER.error("Cannot open file for properties. Check permissions!");
            throw new RuntimeException(e);
        }
        return this;
    }

    public void zipDistribution(String pathToZip, OutputStream os) throws IOException {
        ZipUtils.zipFolder(Paths.get(pathToZip), os);
    }

    public DebeziumServerDistributionBuilder generateOperatorCR() {
        this.customDebeziumServer.toYaml();
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            byte[] cr = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsBytes(this.customDebeziumServer.getOperatorCR());
            File f = new File(String.format("%s/010_custom-debezium-server.yaml", this.pathToProject));
            try (FileOutputStream fs = new FileOutputStream(f)) {
                fs.write(cr);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
}
