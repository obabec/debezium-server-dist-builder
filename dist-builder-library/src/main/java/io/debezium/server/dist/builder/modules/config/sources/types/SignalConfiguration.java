package io.debezium.server.dist.builder.modules.config.sources.types;

import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import io.debezium.server.dist.builder.modules.config.YamlConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;


@Getter
@Setter
public class SignalConfiguration implements PropertiesConfig, YamlConfig {

    protected String topic;

    protected List<String> bootstrapServers;

    protected Integer pollTimeoutMs;

    public SignalConfiguration() {
    }

    @Override
    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        String PREFIX = "debezium.source.signal.kafka";
        builder.put(PREFIX + "topic", topic);
        if (bootstrapServers != null) {
            builder.put(PREFIX + "bootstrap.servers", String.join(",", bootstrapServers));
        }
        builder.put(PREFIX + "poll.timeout.ms", pollTimeoutMs);
    }
    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        getCommonConfig(propertiesBuilder);
        return propertiesBuilder.getProperties();
    }



    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getCommonConfig(yamlBuilder);
        return yamlBuilder.getYaml();
    }
}
