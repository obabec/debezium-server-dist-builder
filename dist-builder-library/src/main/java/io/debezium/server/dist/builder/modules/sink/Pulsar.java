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
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    @Override
    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put(SINK_NODE_CONFIG_PREFIX + "pulsar.timeout", pulsarTimeout);
        builder.putAllWithPrefix("debezium.sink.pulsar.client.", pulsarClient);
        builder.putAllWithPrefix("debezium.sink.pulsar.producer.", pulsarProducer);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pulsar.null.key", pulsarNullKey);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pulsar.tenant", pulsarTenant);
        builder.put(SINK_NODE_CONFIG_PREFIX + "pulsar.namespace", pulsarNamespace);

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
