package io.debezium.server.dist.builder.modules.source.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@Getter
@Setter
@Buildable
public class KafkaStorageConfig implements SchemaHistoryStorage {
    private final String ARTIFACT_ID = "debezium-storage-kafka";

    private String topic;
    private List<String> bootstrapServers;
    private Integer recoveryPollIntervalMs;
    private Integer recoveryAttempts;
    private Integer queryTimeoutsMs;
    private Integer createTimeoutMs;


    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put("kafka.topic", topic);
        builder.put("kafka.bootstrap.servers", bootstrapServers);
        builder.put("kafka.recovery.poll.interval.ms", recoveryPollIntervalMs);
        builder.put("kafka.recovery.attempts", recoveryAttempts);
        builder.put("kafka.query.timeouts.ms", queryTimeoutsMs);
        builder.put("kafka.create.timeout.ms", createTimeoutMs);
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getCommonConfig(yamlBuilder);
        return yamlBuilder.getYaml();
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        getCommonConfig(propertiesBuilder);
        return propertiesBuilder.getProperties();
    }
}
