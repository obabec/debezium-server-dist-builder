package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class PubSubLite implements ModuleNode{
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "pubsublite";

    private final String type = "pubsublite";

    private String pubsubliteProjectId;

    private String pubsubliteRegion;

    private Boolean pubsubliteOrderingEnabled;

    private String pubsubliteNullKey;

    private Integer pubsubliteWaitMessageDeliveryTimeoutMs;

    private String ioDebeziumServerPubsubPub_Sub_Lite_Change_ConsumerPublish_Builder;

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

