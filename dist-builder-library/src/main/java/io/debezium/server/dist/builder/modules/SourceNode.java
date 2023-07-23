package io.debezium.server.dist.builder.modules;

import io.sundr.builder.annotations.Buildable;


public interface SourceNode extends ModuleNode{
    String DEBEZIUM_CONNECTOR_PREFIX = "debezium-connector-";
}
