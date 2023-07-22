package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.config.sinks.types.RedisMessageFormat;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Redis implements ModuleNode {
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

    private String ioDebeziumServerStream_Name_Mapper;




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
}
