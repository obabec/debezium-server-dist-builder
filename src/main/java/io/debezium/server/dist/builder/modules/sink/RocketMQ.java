package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class RocketMQ implements ModuleNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "rocketmq";

    private final String type = "rocketmq";

    private String rocketmqProducerNameSrvAddr;

    private String rocketmqProducerGroup;

    private String rocketmqProducerMaxMessageSize;

    private String rocketmqProducerSendMsgTimeout;

    private Boolean rocketmqProducerAclEnabled;

    private String rocketmqProducerAccessKey;

    private String rocketmqProducerSecretKey;

    private String orgApacheRocketmqClientProducerDefault_MQ_Producer;

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
