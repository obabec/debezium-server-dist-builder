package io.debezium.server.dist.builder.modules;

import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.config.YamlConfig;


public interface SinkNode extends ModuleNode, PropertiesConfig, YamlConfig {
    String DEBEZIUM_SERVER_PREFIX = "debezium-server-";
    String SINK_NODE_CONFIG_PREFIX = "debezium.sink.";

    String getType();
}
