package io.debezium.server.dist.builder;

import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.source.offset.InternalSchemaHistory;
import io.debezium.server.dist.builder.modules.source.offset.OffsetStorage;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;


@Getter
@Setter
@Buildable
public class DebeziumServer implements PropertiesConfig {
    private SourceNode sourceNode;
    private SinkNode sinkNode;

    // Generic server config
    private InternalSchemaHistory internalSchemaHistory;
    private OffsetStorage offsetStorage;

    public DebeziumServer() {
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder builder = new PropertiesBuilder();
        builder.putAll(sourceNode);
        builder.putAll(sinkNode);
        builder.putAll(internalSchemaHistory);
        builder.putAll(offsetStorage);
        return builder.getProperties();
    }
}
