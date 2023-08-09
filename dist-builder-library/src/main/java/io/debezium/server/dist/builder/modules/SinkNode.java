package io.debezium.server.dist.builder.modules;

import io.debezium.server.dist.builder.modules.config.PropertiesConfig;


public interface SinkNode extends ModuleNode, PropertiesConfig {
    String DEBEZIUM_SERVER_PREFIX = "debezium-server-";
    String SINK_NODE_CONFIG_PREFIX = "debezium.sink.";
}
