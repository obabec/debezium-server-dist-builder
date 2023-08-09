package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Properties;


@Buildable
@Getter
@Setter
public class Pulsar implements SinkNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "pulsar";

    private final String type = "pulsar";

    private Integer pulsarTimeout;

    private HashMap<String, Object> pulsarClient;
    private HashMap<String, Object> pulsarProducer;

    private String pulsarNullKey;

    private String pulsarTenant;

    private String pulsarNamespace;

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
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pulsar.timeout", pulsarTimeout);
        propertiesBuilder.putAllWithPrefix("debezium.sink.pulsar.client.", pulsarClient);
        propertiesBuilder.putAllWithPrefix("debezium.sink.pulsar.producer.", pulsarProducer);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pulsar.null.key", pulsarNullKey);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pulsar.tenant", pulsarTenant);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pulsar.namespace", pulsarNamespace);

        propertiesBuilder.put("io.debezium.server.StreamNameMapper", ioDebeziumServerStreamNameMapper);

        return propertiesBuilder.getProperties();
    }
}
