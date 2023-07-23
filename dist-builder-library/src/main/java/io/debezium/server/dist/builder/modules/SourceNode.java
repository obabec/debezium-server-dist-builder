package io.debezium.server.dist.builder.modules;

import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.sundr.builder.annotations.Buildable;


public interface SourceNode extends ModuleNode, PropertiesConfig {
    String DEBEZIUM_CONNECTOR_PREFIX = "debezium-connector-";
}
