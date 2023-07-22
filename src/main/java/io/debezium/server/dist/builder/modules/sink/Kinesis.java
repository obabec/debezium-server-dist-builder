package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.config.sinks.SinkConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Kinesis extends SinkConfig implements ModuleNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "kinesis";

    private final String type = "kinesis";

    private String kinesisRegion;

    private String kinesisEndpoint;

    private String kinesisCredentialsProfile;

    private String kinesisNullKey;

    // Special encoding - software.amazon.awssdk.services.kinesis.KinesisClient
    private String softwareAmazonAwssdkServicesKinesisKinesisClient;

    // Special encoding - io.debezium.server.StreamNameMapper
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


}
