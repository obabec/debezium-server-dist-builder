package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Properties;



@Buildable
@Getter
@Setter
public class Pravega implements SinkNode, PropertiesConfig {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "pravega";

    private final String type = "pravega";

    private String pravegaControllerUri;

    private String pravegaScope;

    private Boolean pravegaTransaction;

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
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pravega.controller.uri", pravegaControllerUri);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pravega.scope", pravegaScope);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "pravega.transaction", pravegaTransaction.toString());

        propertiesBuilder.put("io.debezium.server.StreamNameMapper", ioDebeziumServerStreamNameMapper);

        return propertiesBuilder.getProperties();
    }
}