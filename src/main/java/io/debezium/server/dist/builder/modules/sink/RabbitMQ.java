package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Properties;

public class RabbitMQ implements ModuleNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "rabbitmq";

    private final String type = "rabbitmq";

    private String rabbitmqConnectionHost;

    private Integer rabbitmqConnectionPort;

    private Properties rabbitmqConnection;

    private Integer rabbitmqAck_Timeout;

    private String rabbitmqRouting_Key;

    private String ioDebeziumServerStream_Name_Mapper;

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
