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
public class Kinesis implements SinkNode {
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
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put(SINK_NODE_CONFIG_PREFIX + "kinesis.region", kinesisRegion);
        builder.put(SINK_NODE_CONFIG_PREFIX + "kinesis.endpoint", kinesisEndpoint);
        builder.put(SINK_NODE_CONFIG_PREFIX + "kinesis.credentials.profile", kinesisCredentialsProfile);
        builder.put(SINK_NODE_CONFIG_PREFIX + "kinesis.null.key", kinesisNullKey);

        builder.put("software.amazon.awssdk.services.kinesis.KinesisClient", softwareAmazonAwssdkServicesKinesisKinesisClient);
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
