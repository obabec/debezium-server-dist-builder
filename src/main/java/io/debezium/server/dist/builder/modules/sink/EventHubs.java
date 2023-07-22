package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class EventHubs implements ModuleNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "eventhubs";

    private final String type = "eventhubs";

    private String eventhubsConnectionstring;

    private String eventhubsHubname;

    private String eventhubsPartitionid;

    private String eventhubsPartitionkey;

    private String eventhubsMaxbatchsize;

    private String comAzureMessagingEventhubsEvent_Hub_Producer_Client;


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
}
