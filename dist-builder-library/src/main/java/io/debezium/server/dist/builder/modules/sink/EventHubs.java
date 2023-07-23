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
public class EventHubs implements SinkNode, PropertiesConfig {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "eventhubs";

    private final String type = "eventhubs";

    private String eventhubsConnectionstring;

    private String eventhubsHubname;

    private String eventhubsPartitionid;

    private String eventhubsPartitionkey;

    private String eventhubsMaxbatchsize;

    private String comAzureMessagingEventhubsEventHubProducerClient;


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
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "eventhubs.connectionstring", eventhubsConnectionstring);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "eventhubs.hubname", eventhubsHubname);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "eventhubs.partitionid", eventhubsPartitionid);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "eventhubs.partitionkey", eventhubsPartitionkey);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "eventhubs.maxbatchsize", eventhubsMaxbatchsize);
        propertiesBuilder.put("com.azure.messaging.eventhubs.EventHubProducerClient", comAzureMessagingEventhubsEventHubProducerClient);

        return propertiesBuilder.getProperties();
    }
}
