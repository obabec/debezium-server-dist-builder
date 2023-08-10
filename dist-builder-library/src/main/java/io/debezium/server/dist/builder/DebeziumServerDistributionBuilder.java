package io.debezium.server.dist.builder;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;


//TODO:
// Figure problem with maven home automatic detection or maybe let this responsibility to CLI ?


public class DebeziumServerDistributionBuilder {
    private static final String SERVER_FOLDER = "debezium-server-dist";
    private static final Logger LOGGER = LoggerFactory.getLogger(DebeziumServerDistributionBuilder.class);
    private static final String POM_TEMPLATE = "template.xslt";
    private DebeziumServer debeziumServer;
    private String git_repo = null;
    private String pathToProject = null;
    private String pathToTargetPom;
    private Git distRepo;
    private Document pom;
    private Node dependenciesNode;

    public DebeziumServerDistributionBuilder() {
    }

    public DebeziumServerDistributionBuilder withVersion(String version) {
        ModuleDependencyBuilder.version = version;
        return this;
    }

    public DebeziumServerDistributionBuilder withDebeziumServer(DebeziumServer server) {
        this.debeziumServer = server;
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
            NodeList dependencyNodes = pom.getElementsByTagName("dependencies");
            if (dependencyNodes.getLength() == 0) {
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
        } catch (IOException | TransformerException e) {
            throw new RuntimeException(e);
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
        if (git_repo != null) {
            LOGGER.trace("Cloning the remote repository");
            cloneDistFolder();
            LOGGER.trace("Cloning done");
        }
        LOGGER.trace("Parsing pom document template");
        preparePomDocument();
        LOGGER.trace("Started build of pom file");
        removeCurrentPom();
        dependenciesNode.appendChild(debeziumServer.getSinkNode().buildNode(pom));
        dependenciesNode.appendChild(debeziumServer.getSourceNode().buildNode(pom));
        LOGGER.trace("Finished building pom file");
        write(pathToTargetPom);
        return this;
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
        propertiesBuilder.putAll(debeziumServer);
        String pathToPropertiesFile = String.format("%s/src/main/resources/distro/conf/application.properties", pathToProject);
        try {
            String properties = ConfigurationSerializer.configToPropertiesFile(debeziumServer);
            FileOutputStream outputStream = new FileOutputStream(pathToPropertiesFile);
            outputStream.write(properties.getBytes());
            outputStream.close();
        } catch (IOException e) {
            LOGGER.error("Cannot open file for properties. Check permissions!");
            throw new RuntimeException(e);
        }
        return this;
    }

    public DebeziumServerDistributionBuilder generateOperatorCR() {
        return this;
    }
}
