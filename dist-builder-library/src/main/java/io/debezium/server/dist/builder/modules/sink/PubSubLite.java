package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Properties;


@Buildable
@Getter
@Setter
public class PubSubLite implements SinkNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "pubsublite";

    private final String type = "pubsublite";

    private String pubsubliteProjectId;

    private String pubsubliteRegion;

    private Boolean pubsubliteOrderingEnabled;

    private String pubsubliteNullKey;

    private Integer pubsubliteWaitMessageDeliveryTimeoutMs;

    private String ioDebeziumServerPubsubPubSubLiteChangeConsumerPublishBuilder;

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

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();

        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "type", type);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsublite.project.id", pubsubliteProjectId);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsublite.region", pubsubliteRegion);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsublite.ordering.enabled", pubsubliteOrderingEnabled);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsublite.null.key", pubsubliteNullKey);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pubsublite.wait.message.delivery.timeout.ms", pubsubliteWaitMessageDeliveryTimeoutMs);
        propertiesBuilder.put("io.debezium.server.StreamNameMapper", ioDebeziumServerStreamNameMapper);
        propertiesBuilder.put("io.debezium.server.pubsub.PubSubLiteChangeConsumer.PublisherBuilder", ioDebeziumServerPubsubPubSubLiteChangeConsumerPublishBuilder);

        return propertiesBuilder.getProperties();
    }
}

