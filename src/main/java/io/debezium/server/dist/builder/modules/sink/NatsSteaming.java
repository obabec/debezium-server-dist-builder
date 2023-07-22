package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class NatsSteaming implements ModuleNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "nats-streaming";

    private final String type = "nats-streaming";

    // nats-streaming.url
    private String nats_streamingUrl;

    private String nats_streamingClusterId;

    private String nats_streamingClientId;


    private String ioNatsStreamingStreaming_Connection;
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
