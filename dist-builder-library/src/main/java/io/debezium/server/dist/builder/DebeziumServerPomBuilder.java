package io.debezium.server.dist.builder;

import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.sink.Kafka;
import io.debezium.server.dist.builder.modules.sink.Pulsar;
import io.debezium.server.dist.builder.modules.source.Mysql;
import io.debezium.server.dist.builder.modules.source.Postgres;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// TODO: Add all nodes to builder, prepare configuration builder for the nodes
public class DebeziumServerPomBuilder {

    private final Document pom;
    private static final Logger logger = LoggerFactory.getLogger(DebeziumServerPomBuilder.class);

    private final List<ModuleNode> moduleNodes;
    private final Node dependenciesNode;
    private static final String POM_TEMPLATE = "template.xslt";

    private boolean checkModuleNotExists(ModuleNode moduleNode) {
       for (ModuleNode listNode : moduleNodes) {
           if (listNode.getClass().equals(moduleNode.getClass())) {
               return false;
           }
       }
       return true;
    }

    public DebeziumServerPomBuilder() {
        DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
        try (InputStream is = MainClass.class.getClassLoader().getResourceAsStream("base-pom.xml")) {
            DocumentBuilder docBuilder = docFac.newDocumentBuilder();
            pom = docBuilder.parse(is);
            NodeList dependencyNodes = pom.getElementsByTagName("dependencies");
            if (dependencyNodes.getLength() == 0) {
                logger.error("[ERROR] Base pom file from resources is corrupted!");
                throw new RuntimeException("Base pom file from resources is corrupted!");
            }
            dependenciesNode = dependencyNodes.item(0);
            moduleNodes = new ArrayList<>();
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public DebeziumServerPomBuilder addMysqlSource() {
        Mysql node = new Mysql();
        if (checkModuleNotExists(node)) {
            moduleNodes.add(node);
        }
        return this;
    }

    public DebeziumServerPomBuilder addPostgresSource() {
        Postgres node = new Postgres();
        if (checkModuleNotExists(node)) {
            moduleNodes.add(node);
        }
        return this;
    }

    public DebeziumServerPomBuilder addPulsarSink() {
        Pulsar node = new Pulsar();
        if (checkModuleNotExists(node)) {
            moduleNodes.add(node);
        }
        return this;
    }

    public DebeziumServerPomBuilder addKafkaSink() {
        Kafka node = new Kafka();
        if (checkModuleNotExists(node)) {
            moduleNodes.add(node);
        }
        return this;
    }

    public DebeziumServerPomBuilder build() {
        logger.trace("Started build of pom file");
        for (ModuleNode node : moduleNodes) {
            logger.trace("Adding " + node.toString() + " module to pom file");
            dependenciesNode.appendChild(node.buildNode(pom));
        }
        logger.trace("Finished building pom file");
        return this;
    }
    public void write(String pathToPom) {
        try (FileOutputStream output = new FileOutputStream(pathToPom)) {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(
                    new StreamSource(MainClass.class.getClassLoader().getResourceAsStream(POM_TEMPLATE)));

            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
            DOMSource source = new DOMSource(pom);
            StreamResult result = new StreamResult(output);

            transformer.transform(source, result);
        } catch (IOException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
