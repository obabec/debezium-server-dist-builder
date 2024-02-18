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
public class RabbitMQ implements SinkNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "rabbitmq";

    private final String type = "rabbitmq";

    private String rabbitmqConnectionHost;

    private Integer rabbitmqConnectionPort;

    private HashMap<String, Object> rabbitmqConnection;

    private Integer rabbitmqAckTimeout;

    private String rabbitmqRoutingKey;

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
        builder.put(SINK_NODE_CONFIG_PREFIX + "rabbitmq.connection.host", rabbitmqConnectionHost);
        builder.put(SINK_NODE_CONFIG_PREFIX + "rabbitmq.connection.port", rabbitmqConnectionPort);

        builder.putAllWithPrefix("debezium.sink.rabbitmq.connection.", rabbitmqConnection);
        builder.put(SINK_NODE_CONFIG_PREFIX + "rabbitmq.ackTimeout", rabbitmqAckTimeout);
        builder.put(SINK_NODE_CONFIG_PREFIX + "rabbitmq.routingKey", rabbitmqRoutingKey);

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
