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
public class RocketMQ implements SinkNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "rocketmq";

    private final String type = "rocketmq";

    private String rocketmqProducerNameSrvAddr;

    private String rocketmqProducerGroup;

    private String rocketmqProducerMaxMessageSize;

    private String rocketmqProducerSendMsgTimeout;

    private Boolean rocketmqProducerAclEnabled;

    private String rocketmqProducerAccessKey;

    private String rocketmqProducerSecretKey;

    private String orgApacheRocketmqClientProducerDefaultMQProducer;

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
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "rocketmq.producer.name.srv.addr", rocketmqProducerNameSrvAddr);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "rocketmq.producer.group", rocketmqProducerGroup);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "rocketmq.producer.max.message.size", rocketmqProducerMaxMessageSize);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "rocketmq.producer.send.msg.timeout", rocketmqProducerSendMsgTimeout);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "rocketmq.producer.acl.enabled", rocketmqProducerAclEnabled);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "rocketmq.producer.access.key", rocketmqProducerAccessKey);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "rocketmq.producer.secret.key", rocketmqProducerSecretKey);

        propertiesBuilder.put("io.debezium.server.StreamNameMapper", ioDebeziumServerStreamNameMapper);
        propertiesBuilder.put("org.apache.rocketmq.client.producer.DefaultMQProducer", orgApacheRocketmqClientProducerDefaultMQProducer);

        return propertiesBuilder.getProperties();
    }
}
