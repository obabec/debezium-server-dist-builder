package io.debezium.server.dist.builder.modules.source.storage;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Properties;

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

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        propertiesBuilder.put("kafka.topic", topic);
        propertiesBuilder.put("kafka.bootstrap.servers", bootstrapServers);
        propertiesBuilder.put("kafka.recovery.poll.interval.ms", recoveryPollIntervalMs);
        propertiesBuilder.put("kafka.recovery.attempts", recoveryAttempts);
        propertiesBuilder.put("kafka.query.timeouts.ms", queryTimeoutsMs);
        propertiesBuilder.put("kafka.create.timeout.ms", createTimeoutMs);
        return propertiesBuilder.getProperties();
    }
}
