/*
 * MIT License
 *
 * Copyright (c) [2024] [Ondrej Babec <ond.babec@gmail.com>]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE
 * ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.debezium.server.dist.builder.modules.sink;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

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
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put(SINK_NODE_CONFIG_PREFIX + "rocketmq.producer.name.srv.addr", rocketmqProducerNameSrvAddr);
        builder.put(SINK_NODE_CONFIG_PREFIX + "rocketmq.producer.group", rocketmqProducerGroup);
        builder.put(SINK_NODE_CONFIG_PREFIX + "rocketmq.producer.max.message.size", rocketmqProducerMaxMessageSize);
        builder.put(SINK_NODE_CONFIG_PREFIX + "rocketmq.producer.send.msg.timeout", rocketmqProducerSendMsgTimeout);
        builder.put(SINK_NODE_CONFIG_PREFIX + "rocketmq.producer.acl.enabled", rocketmqProducerAclEnabled);
        builder.put(SINK_NODE_CONFIG_PREFIX + "rocketmq.producer.access.key", rocketmqProducerAccessKey);
        builder.put(SINK_NODE_CONFIG_PREFIX + "rocketmq.producer.secret.key", rocketmqProducerSecretKey);

        builder.put("io.debezium.server.StreamNameMapper", ioDebeziumServerStreamNameMapper);
        builder.put("org.apache.rocketmq.client.producer.DefaultMQProducer", orgApacheRocketmqClientProducerDefaultMQProducer);
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
