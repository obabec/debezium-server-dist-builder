package io.debezium.server.dist.builder.modules;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public interface ModuleNode {
    Node buildNode(Document document);
    String GROUP_ID = "io.debezium";
    String DEBEZIUM_CONNECTOR_PREFIX = "debezium-connector-";

    String DEBEZIUM_SERVER_PREFIX = "debezium-server-";
}
