package io.debezium.server.dist.builder.modules.config.sources.types;

import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Properties;


@Getter
@Setter
public class SignalConfiguration implements PropertiesConfig {

    protected String topic;

    protected List<String> bootstrapServers;

    protected Integer pollTimeoutMs;

    public SignalConfiguration() {
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        String PREFIX = "debezium.source.signal.kafka";
        propertiesBuilder.put(PREFIX + "topic", topic);
        if (bootstrapServers != null) {
            propertiesBuilder.put(PREFIX + "bootstrap.servers", String.join(",", bootstrapServers));
        }
        propertiesBuilder.put(PREFIX + "poll.timeout.ms", pollTimeoutMs);

        return propertiesBuilder.getProperties();
    }
}
