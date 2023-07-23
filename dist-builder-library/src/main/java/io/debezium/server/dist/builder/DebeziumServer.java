package io.debezium.server.dist.builder;

import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Properties;


//TODO: Add last missing generic server configuration

@Getter
@Setter
@Buildable
public class DebeziumServer implements PropertiesConfig {
    private SourceNode sourceNode;
    private SinkNode sinkNode;


    @Override
    public Properties toProperties() {
        PropertiesBuilder builder = new PropertiesBuilder();
        builder.putAll(sourceNode.toProperties());
        builder.putAll(sinkNode.toProperties());

        return builder.getProperties();
    }
}
