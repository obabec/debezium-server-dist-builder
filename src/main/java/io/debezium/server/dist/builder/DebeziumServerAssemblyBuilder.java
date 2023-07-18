package io.debezium.server.dist.builder;

import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.sink.KafkaSinkNode;
import io.debezium.server.dist.builder.modules.sink.PulsarSinkNode;
import io.debezium.server.dist.builder.modules.source.MysqlSourceNode;
import io.debezium.server.dist.builder.modules.source.PostgresSourceNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


// Mozna tady jit pres moduleSety ? To by bylo asi uzitecnejsi nez excludy
// 
public class DebeziumServerAssemblyBuilder {
    private final Document assembly;
    private static final Logger logger = LoggerFactory.getLogger(DebeziumServerPomBuilder.class);

    private final List<ModuleNode> moduleNodes;
    private final Node excludeNodes;
    private static final String POM_TEMPLATE = "template.xslt";

    private boolean checkModuleNotExists(ModuleNode moduleNode) {
        for (ModuleNode listNode : moduleNodes) {
            if (listNode.getClass().equals(moduleNode.getClass())) {
                return false;
            }
        }
        return true;
    }

    public DebeziumServerAssemblyBuilder() {
        DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
        try (InputStream is = MainClass.class.getClassLoader().getResourceAsStream("base-assembly.xml")) {
            DocumentBuilder docBuilder = docFac.newDocumentBuilder();
            assembly = docBuilder.parse(is);
            NodeList excludeNode = assembly.getElementsByTagName("excludes");
            if (excludeNode.getLength() == 0) {
                logger.error("[ERROR] Base pom file from resources is corrupted!");
                throw new RuntimeException("Base pom file from resources is corrupted!");
            }
            excludeNodes = excludeNode.item(0);
            moduleNodes = new ArrayList<>();

        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public DebeziumServerAssemblyBuilder addMysqlSource() {
        MysqlSourceNode node = new MysqlSourceNode();
        if (checkModuleNotExists(node)) {
            moduleNodes.add(node);
        }
        return this;
    }

    public DebeziumServerAssemblyBuilder addPostgresSource() {
        PostgresSourceNode node = new PostgresSourceNode();
        if (checkModuleNotExists(node)) {
            moduleNodes.add(node);
        }
        return this;
    }

    public DebeziumServerAssemblyBuilder addPulsarSink() {
        PulsarSinkNode node = new PulsarSinkNode();
        if (checkModuleNotExists(node)) {
            moduleNodes.add(node);
        }
        return this;
    }

    public DebeziumServerAssemblyBuilder addKafkaSink() {
        KafkaSinkNode node = new KafkaSinkNode();
        if (checkModuleNotExists(node)) {
            moduleNodes.add(node);
        }
        return this;
    }
}
