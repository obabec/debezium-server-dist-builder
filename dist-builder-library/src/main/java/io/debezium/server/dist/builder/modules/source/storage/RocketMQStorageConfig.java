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


@Buildable
@Getter
@Setter
public class RocketMQStorageConfig implements SchemaHistoryStorage {
    private final String ARTIFACT_ID = "debezium-storage-rocketmq";

    private String topic;
    private String nameSrvAddr;
    private Boolean aclEnabled;

    private String accessKey;
    private String secretKey;

    private Integer recoveryAttempts;
    private Integer recoveryPollIntervalMs;
    private Integer storeRecordTimeoutMs;


    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        propertiesBuilder.put("rocketmq.topic", topic);
        propertiesBuilder.put("rocketmq.name.srv.addr", nameSrvAddr);
        propertiesBuilder.putBoolean("rocketmq.acl.enabled", aclEnabled);
        propertiesBuilder.put("rocketmq.access.key", accessKey);
        propertiesBuilder.put("rocketmq.secret.key", secretKey);
        propertiesBuilder.put("rocketmq.recovery.attempts", recoveryAttempts);
        propertiesBuilder.put("rocketmq.recovery.poll.interval.ms", recoveryPollIntervalMs);
        propertiesBuilder.put("rocketmq.store.record.timeout.ms", storeRecordTimeoutMs);

        return propertiesBuilder.getProperties();
    }

    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }
}
