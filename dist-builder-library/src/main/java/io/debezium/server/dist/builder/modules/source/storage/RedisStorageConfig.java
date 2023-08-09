package io.debezium.server.dist.builder.modules.source.storage;

import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;


@Buildable
@Getter
@Setter
public class RedisStorageConfig implements StorageConfig {
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
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        propertiesBuilder.put("redis.address", address);
        propertiesBuilder.put("redis.user", user);
        propertiesBuilder.put("redis.password", password);
        propertiesBuilder.putBoolean("redis.ssl.enabled", sslEnabled);
        propertiesBuilder.put("redis.key", key);
        propertiesBuilder.putBoolean("redis.wait.enabled", waitEnabled);
        propertiesBuilder.put("redis.wait.timeout.ms", waitTimeoutMs);
        propertiesBuilder.putBoolean("redis.wait.retry.enabled", waitRetryEnabled);
        propertiesBuilder.put("redis.wait.retry.delay.ms", waitRetryDelayMs);
        return propertiesBuilder.getProperties();
    }
}
