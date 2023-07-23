package io.debezium.server.dist.builder.modules;

import io.sundr.builder.annotations.Buildable;


public interface SinkNode extends ModuleNode {
    String DEBEZIUM_SERVER_PREFIX = "debezium-server-";
    String SINK_NODE_CONFIG_PREFIX = "debezium.sink.";
}
