package io.debezium.server.dist.builder.modules.source.storage;

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

import java.util.HashMap;
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
    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put("rocketmq.topic", topic);
        builder.put("rocketmq.name.srv.addr", nameSrvAddr);
        builder.putBoolean("rocketmq.acl.enabled", aclEnabled);
        builder.put("rocketmq.access.key", accessKey);
        builder.put("rocketmq.secret.key", secretKey);
        builder.put("rocketmq.recovery.attempts", recoveryAttempts);
        builder.put("rocketmq.recovery.poll.interval.ms", recoveryPollIntervalMs);
        builder.put("rocketmq.store.record.timeout.ms", storeRecordTimeoutMs);
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

    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }
}
