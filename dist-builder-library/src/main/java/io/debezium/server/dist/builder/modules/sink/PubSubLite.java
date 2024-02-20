package io.debezium.server.dist.builder.modules.sink;

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

import java.util.HashMap;
import java.util.List;
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
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsublite.project.id", pubsubliteProjectId);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsublite.region", pubsubliteRegion);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsublite.ordering.enabled", pubsubliteOrderingEnabled);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsublite.null.key", pubsubliteNullKey);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pubsublite.wait.message.delivery.timeout.ms", pubsubliteWaitMessageDeliveryTimeoutMs);
        builder.put("io.debezium.server.StreamNameMapper", ioDebeziumServerStreamNameMapper);
        builder.put("io.debezium.server.pubsub.PubSubLiteChangeConsumer.PublisherBuilder", ioDebeziumServerPubsubPubSubLiteChangeConsumerPublishBuilder);
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

