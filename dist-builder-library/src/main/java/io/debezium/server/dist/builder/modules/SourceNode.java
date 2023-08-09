package io.debezium.server.dist.builder.modules;

import io.debezium.server.dist.builder.modules.config.PropertiesConfig;


public interface SourceNode extends ModuleNode, PropertiesConfig {
    String DEBEZIUM_CONNECTOR_PREFIX = "debezium-connector-";
}
