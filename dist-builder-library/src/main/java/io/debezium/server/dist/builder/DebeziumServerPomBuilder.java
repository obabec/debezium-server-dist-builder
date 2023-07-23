package io.debezium.server.dist.builder;

import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.sink.Kafka;
import io.debezium.server.dist.builder.modules.sink.Pulsar;
import io.debezium.server.dist.builder.modules.source.Mysql;
import io.debezium.server.dist.builder.modules.source.Postgres;
import io.sundr.builder.annotations.Buildable;
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


//TODO: Here should be added evertyhing from Project builder
// There should be added option to either stick with github repo of for example provide path to zip file and work with that
// Figure problem with maven home automatic detection or maybe let this responsibility to CLI ?
@Buildable
public class DebeziumServerPomBuilder {

    private final Document pom;
    private static final Logger logger = LoggerFactory.getLogger(DebeziumServerPomBuilder.class);

    private DebeziumServer debeziumServer;
    private final Node dependenciesNode;
    private static final String POM_TEMPLATE = "template.xslt";

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
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public DebeziumServerPomBuilder build() {
        logger.trace("Started build of pom file");
        dependenciesNode.appendChild(debeziumServer.getSinkNode().buildNode(pom));
        dependenciesNode.appendChild(debeziumServer.getSourceNode().buildNode(pom));
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
