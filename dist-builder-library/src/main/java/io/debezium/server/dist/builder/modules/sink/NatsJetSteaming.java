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
public class NatsJetSteaming implements SinkNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "nats-jetstream";

    private final String type = "nats-jetstream";

    private String natsJetstreamUrl;

    private Boolean natsJetstreamCreateStream;

    private List<String> natsJetstreamSubjects;

    private String natsJetstreamStorage;

    private String ioNatsClientJetStream;

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
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "nats-jetstream.url", natsJetstreamUrl);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "nats-jetstream.create-stream", natsJetstreamCreateStream);
        propertiesBuilder.putList(SINK_NODE_CONFIG_PREFIX + "nats-jetstream.subjects", natsJetstreamSubjects);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "nats-jetstream.storage", natsJetstreamStorage);

        propertiesBuilder.put("io.nats.client.JetStream", ioNatsClientJetStream);
        propertiesBuilder.put("io.debezium.server.StreamNameMapper", ioDebeziumServerStreamNameMapper);

        return propertiesBuilder.getProperties();
    }
}
