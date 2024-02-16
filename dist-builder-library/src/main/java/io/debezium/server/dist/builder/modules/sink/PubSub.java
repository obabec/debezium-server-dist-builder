package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.SinkNode;
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

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();

        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "type", type);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.project.id", pubsubProjectId);
        propertiesBuilder.putBoolean(SINK_NODE_CONFIG_PREFIX + "pubsub.ordering.enabled", pubsubOrderingEnabled);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.null.key", pubsubNullKey);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.batch.delay.threshold.ms", pubsubBatchDelayThresholdMs);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.batch.element.count.threshold", pubsubBatchElementCountThreshold + "L");
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.batch.request.byte.threshold", pubsubBatchRequestByteThreshold + "L");
        propertiesBuilder.putBoolean(SINK_NODE_CONFIG_PREFIX + "pubsub.flowControl.enabled", pubsubFlowControlEnabled);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.flowControl.max.outstanding.messages", pubsubFlowControlMaxOutstandingMessages);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.flowControl.max.outstanding.bytes", pubsubFlowControlMaxOutstandingBytes);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.retry.total.timeout.ms", pubsubRetryTotalTimeoutMs);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.retry.initial.delay.ms", pubsubRetryInitialDelayMs);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.retry.delay.multiplier", pubsubRetryDelayMultiplier);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.retry.max.delay.ms", pubsubRetryMaxDelayMs);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.retry.initial.rpc.timeout.ms", pubsubRetryInitialRpcTimeoutMs);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.retry.rpc.timeout.multiplier", pubsubRetryRpcTimeoutMultiplier);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.retry.max.rpc.timeout.ms", pubsubRetryMaxRpcTimeoutMs);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.wait.message.delivery.timeout.ms", pubsubWaitMessageDeliveryTimeoutMs);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsub.address", pubsubAddress);

        propertiesBuilder.put("io.debezium.server.StreamNameMapper", ioDebeziumServerStreamNameMapper);
        propertiesBuilder.put("io.debezium.server.pubsub.PubSubChangeConsumer.PublisherBuilder", ioDebeziumServerPubsubPubSubChangeConsumerPublishBuilder);

        return propertiesBuilder.getProperties();
    }
}
