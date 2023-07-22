package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class PubSub implements ModuleNode {
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
    private Boolean pubsubFlow_ControlEnabled;

    private String pubsubFlow_ControlMaxOutstandingMessages;
    private String pubsubFlow_ControlMaxOutstandingBytes;

    private Integer pubsubRetryTotalTimeoutMs;

    private Integer pubsubRetryInitialDelayMs;

    private Long pubsubRetryDelayMultiplier;

    private String pubsubRetryMaxDelayMs;

    private Integer pubsubRetryInitialRpcTimeoutMs;

    private Long pubsubRetryRpcTimeoutMultiplier;

    private Integer pubsubRetryMaxRpcTimeoutMs;

    private Integer pubsubWaitMessageDeliveryTimeoutMs;

    private String pubsubAddress;

    private String ioDebeziumServerPubsubPub_Sub_Change_ConsumerPublish_Builder;

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
