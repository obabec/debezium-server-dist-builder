package io.debezium.server.dist.builder.modules;

import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.config.YamlConfig;


public interface SourceNode extends ModuleNode, PropertiesConfig, YamlConfig {
    String DEBEZIUM_CONNECTOR_PREFIX = "debezium-connector-";

    String getConnectorClass();
}
