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
public class RedisStorageConfig implements StorageConfig, SchemaHistoryStorage {
    private final String ARTIFACT_ID = "debezium-storage-redis";

    private String address;
    private String user;
    private String password;

    private Boolean sslEnabled;
    private String key;
    private Boolean waitEnabled;
    private Integer waitTimeoutMs;
    private Boolean waitRetryEnabled;
    private Integer waitRetryDelayMs;

    @Override
    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put("redis.address", address);
        builder.put("redis.user", user);
        builder.put("redis.password", password);
        builder.putBoolean("redis.ssl.enabled", sslEnabled);
        builder.put("redis.key", key);
        builder.putBoolean("redis.wait.enabled", waitEnabled);
        builder.put("redis.wait.timeout.ms", waitTimeoutMs);
        builder.putBoolean("redis.wait.retry.enabled", waitRetryEnabled);
        builder.put("redis.wait.retry.delay.ms", waitRetryDelayMs);
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
