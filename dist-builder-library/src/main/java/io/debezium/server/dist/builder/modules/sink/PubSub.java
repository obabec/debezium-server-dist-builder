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
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


@Buildable
@Getter
@Setter
public class PubSub implements SinkNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "pubsub";

    private final String type = "pubsub";

    private String pubsubProjectId;

    private Boolean pubsubOrderingEnabled;

    private String pubsubNullKey;

    private Integer pubsubBatchDelayThresholdMs;

    // During serialiasing add L to the end of value
    private Integer pubsubBatchElementCountThreshold;

    // During serialiasing add L to the end of value
    private Integer pubsubBatchRequestByteThreshold;

    // difference in naming -- flowControl
    private Boolean pubsubFlowControlEnabled;

    private Long pubsubFlowControlMaxOutstandingMessages;
    private Long pubsubFlowControlMaxOutstandingBytes;

    private Integer pubsubRetryTotalTimeoutMs;

    private Integer pubsubRetryInitialDelayMs;

    private Long pubsubRetryDelayMultiplier;

    private String pubsubRetryMaxDelayMs;

    private Integer pubsubRetryInitialRpcTimeoutMs;

    private Long pubsubRetryRpcTimeoutMultiplier;

    private Integer pubsubRetryMaxRpcTimeoutMs;

    private Integer pubsubWaitMessageDeliveryTimeoutMs;

    private String pubsubAddress;

    private String ioDebeziumServerPubsubPubSubChangeConsumerPublishBuilder;

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
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.project.id", pubsubProjectId);
        builder.putBoolean(SINK_NODE_CONFIG_PREFIX + "pubsub.ordering.enabled", pubsubOrderingEnabled);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.null.key", pubsubNullKey);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.batch.delay.threshold.ms", pubsubBatchDelayThresholdMs);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.batch.element.count.threshold", pubsubBatchElementCountThreshold + "L");
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.batch.request.byte.threshold", pubsubBatchRequestByteThreshold + "L");
        builder.putBoolean(SINK_NODE_CONFIG_PREFIX + "pubsub.flowControl.enabled", pubsubFlowControlEnabled);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.flowControl.max.outstanding.messages", pubsubFlowControlMaxOutstandingMessages);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.flowControl.max.outstanding.bytes", pubsubFlowControlMaxOutstandingBytes);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.retry.total.timeout.ms", pubsubRetryTotalTimeoutMs);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.retry.initial.delay.ms", pubsubRetryInitialDelayMs);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.retry.delay.multiplier", pubsubRetryDelayMultiplier);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.retry.max.delay.ms", pubsubRetryMaxDelayMs);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.retry.initial.rpc.timeout.ms", pubsubRetryInitialRpcTimeoutMs);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.retry.rpc.timeout.multiplier", pubsubRetryRpcTimeoutMultiplier);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.retry.max.rpc.timeout.ms", pubsubRetryMaxRpcTimeoutMs);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.wait.message.delivery.timeout.ms", pubsubWaitMessageDeliveryTimeoutMs);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.address", pubsubAddress);

        builder.put("io.debezium.server.StreamNameMapper", ioDebeziumServerStreamNameMapper);
        builder.put("io.debezium.server.pubsub.PubSubChangeConsumer.PublisherBuilder", ioDebeziumServerPubsubPubSubChangeConsumerPublishBuilder);
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
