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
public class NatsSteaming implements SinkNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "nats-streaming";

    private final String type = "nats-streaming";

    // nats-streaming.url
    private String natsStreamingUrl;

    private String natsStreamingClusterId;

    private String natsStreamingClientId;


    private String ioNatsStreamingStreamingConnection;
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
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "nats-streaming.url", natsStreamingUrl);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "nats-streaming.cluster.id", natsStreamingClusterId);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "nats-streaming.client.id", natsStreamingClientId);

        propertiesBuilder.put("io.nats.streaming.StreamingConnection", ioNatsStreamingStreamingConnection);
        propertiesBuilder.put("io.debezium.server.StreamNameMapper", ioDebeziumServerStreamNameMapper);

        return propertiesBuilder.getProperties();
    }
}
