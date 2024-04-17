/*
 * MIT License
 *
 * Copyright (c) [2024] [Ondrej Babec <ond.babec@gmail.com>]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE
 * ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.debezium.server.dist.builder.modules.sink;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import io.debezium.server.dist.builder.modules.config.sinks.types.RedisMessageFormat;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


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
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put(SINK_NODE_CONFIG_PREFIX + "redis.address", redisAddress);
        builder.put(SINK_NODE_CONFIG_PREFIX + "redis.user", redisUser);
        builder.put(SINK_NODE_CONFIG_PREFIX + "redis.password", redisPassword);
        builder.putBoolean(SINK_NODE_CONFIG_PREFIX + "redis.ssl.enabled", redisSslEnabled);
        builder.put(SINK_NODE_CONFIG_PREFIX + "redis.null.key", redisNullKey);
        builder.put(SINK_NODE_CONFIG_PREFIX + "redis.null.value", redisNullValue);
        builder.put(SINK_NODE_CONFIG_PREFIX + "redis.batch.size", redisBatchSize);
        builder.put(SINK_NODE_CONFIG_PREFIX + "redis.retry.initial.delay.ms", redisRetryInitialDelayMs);
        builder.put(SINK_NODE_CONFIG_PREFIX + "redis.retry.max.delay.ms", redisRetryMaxDelayMs);
        builder.put(SINK_NODE_CONFIG_PREFIX + "redis.connection.timeout.ms", redisConnectionTimeoutMs);
        builder.put(SINK_NODE_CONFIG_PREFIX + "redis.socket.timeout.ms", redisSocketTimeoutMs);
        builder.putBoolean(SINK_NODE_CONFIG_PREFIX + "redis.wait.enabled", redisWaitEnabled);
        builder.put(SINK_NODE_CONFIG_PREFIX + "redis.wait.timeout.ms", redisWaitTimeoutMs);
        builder.putBoolean(SINK_NODE_CONFIG_PREFIX + "redis.wait.retry.enabled", redisWaitRetryEnabled);
        builder.put(SINK_NODE_CONFIG_PREFIX + "redis.wait.retry.delay.ms", redisWaitRetryDelayMs);
        builder.putEnumWithLowerCase(SINK_NODE_CONFIG_PREFIX + "redis.message.format", redisMessageFormat);
        builder.put(SINK_NODE_CONFIG_PREFIX + "redis.memory.threshold.percentage", redisMemoryThresholdPercentage);
        builder.put(SINK_NODE_CONFIG_PREFIX + "redis.memory.limit.mb", redisMemoryLimitMb);
        builder.put("io.debezium.server.StreamNameMapper", ioDebeziumServerStreamNameMapper);
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
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "type", type);
        getCommonConfig(propertiesBuilder);
        return propertiesBuilder.getProperties();
    }
}
