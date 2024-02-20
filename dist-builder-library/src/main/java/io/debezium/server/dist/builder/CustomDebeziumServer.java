package io.debezium.server.dist.builder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.debezium.operator.api.model.ConfigProperties;
import io.debezium.operator.api.model.SinkBuilder;
import io.debezium.operator.api.model.SourceBuilder;
import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.config.YamlConfig;
import io.debezium.server.dist.builder.modules.source.offset.InternalSchemaHistory;
import io.debezium.server.dist.builder.modules.source.offset.OffsetStorage;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;


@Getter
@Setter
@Buildable
public class CustomDebeziumServer implements PropertiesConfig, YamlConfig {
    private SourceNode sourceNode;
    private SinkNode sinkNode;

    // Generic server config
    private InternalSchemaHistory internalSchemaHistory;
    private OffsetStorage offsetStorage;
    private List<Dependency> dependencyList;

    @JsonIgnore
    private io.debezium.operator.api.model.DebeziumServer operatorCR;

    public CustomDebeziumServer() {
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

    @Override
    public HashMap<String, Object> toYaml() {
        if (operatorCR != null) {
            HashMap<String, Object> source = new HashMap<>(internalSchemaHistory.toYaml());
            source.putAll(offsetStorage.toYaml());
            source.putAll(sourceNode.toYaml());
            ConfigProperties sinkProps = new ConfigProperties();
            sinkProps.setAllProps(sinkNode.toYaml());
            SinkBuilder sinkBuilder = new SinkBuilder()
                    .withType(sinkNode.getType())
                    .withConfig(sinkProps);

            ConfigProperties sourceProps = new ConfigProperties();
            sourceProps.setAllProps(source);
            SourceBuilder sourceBuilder = new SourceBuilder()
                    .withSourceClass(sourceNode.getConnectorClass())
                    .withConfig(sourceProps);

            operatorCR.getSpec().setSink(sinkBuilder.build());
            operatorCR.getSpec().setSource(sourceBuilder.build());
            return new HashMap<>(operatorCR.getSpec().asConfiguration().getAsMap());
        }
        return null;
    }

    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
    }
}
