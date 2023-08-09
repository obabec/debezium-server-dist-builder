package io.debezium.server.dist.builder.modules;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public interface ModuleNode {
    String GROUP_ID = "io.debezium";

    Node buildNode(Document document);
}
