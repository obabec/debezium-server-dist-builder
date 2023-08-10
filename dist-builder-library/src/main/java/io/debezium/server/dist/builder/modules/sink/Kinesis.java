package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.sinks.SinkConfig;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Properties;


@Buildable
@Getter
@Setter
public class Kinesis extends SinkConfig implements SinkNode {
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
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID);
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();

        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "type", type);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "kinesis.region", kinesisRegion);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "kinesis.endpoint", kinesisEndpoint);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "kinesis.credentials.profile", kinesisCredentialsProfile);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "kinesis.null.key", kinesisNullKey);

        propertiesBuilder.put("software.amazon.awssdk.services.kinesis.KinesisClient", softwareAmazonAwssdkServicesKinesisKinesisClient);
        propertiesBuilder.put("io.debezium.server.StreamNameMapper", ioDebeziumServerStreamNameMapper);

        return propertiesBuilder.getProperties();
    }
}
