package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.config.sinks.types.RedisMessageFormat;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Properties;


@Buildable
@Getter
@Setter
public class Redis implements SinkNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "redis";

    private final String type = "redis";

    private String redisAddress;

    private String redisUser;

    private String redisPassword;

    private Boolean redisSslEnabled;

    private String redisNullKey;
    private String redisNullValue;
    private Integer redisBatchSize;

    private Integer redisRetryInitialDelayMs;

    private Integer redisRetryMaxDelayMs;

    private Integer redisConnectionTimeoutMs;

    private Integer redisSocketTimeoutMs;

    private Boolean redisWaitEnabled;

    private Integer redisWaitTimeoutMs;

    private Boolean redisWaitRetryEnabled;

    private Integer redisWaitRetryDelayMs;

    private RedisMessageFormat redisMessageFormat;

    private Integer redisMemoryThresholdPercentage;

    private Integer redisMemoryLimitMb;

    private String ioDebeziumServerStreamNameMapper;

    @Override
    public Node buildNode(Document document) {
        return new ModuleDependencyBuilder(document)
                .setGroupId(GROUP_ID)
                .setArtifactId(ARTIFACT_ID)
                .buildDependency();
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();

        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "type", type);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.address", redisAddress);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.user", redisUser);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.password", redisPassword);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.ssl.enabled", redisSslEnabled.toString());
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.null.key", redisNullKey);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.null.value", redisNullValue);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.batch.size", redisBatchSize);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.retry.initial.delay.ms", redisRetryInitialDelayMs);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.retry.max.delay.ms", redisRetryMaxDelayMs);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.connection.timeout.ms", redisConnectionTimeoutMs);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.socket.timeout.ms", redisSocketTimeoutMs);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.wait.enabled", redisWaitEnabled.toString());
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.wait.timeout.ms", redisWaitTimeoutMs);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.wait.retry.enabled", redisWaitRetryEnabled.toString());
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.wait.retry.delay.ms", redisWaitRetryDelayMs);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.message.format", redisMessageFormat.toString().toLowerCase());
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.memory.threshold.percentage", redisMemoryThresholdPercentage);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "redis.memory.limit.mb", redisMemoryLimitMb);


        propertiesBuilder.put("io.debezium.server.StreamNameMapper", ioDebeziumServerStreamNameMapper);

        return propertiesBuilder.getProperties();
    }
}
