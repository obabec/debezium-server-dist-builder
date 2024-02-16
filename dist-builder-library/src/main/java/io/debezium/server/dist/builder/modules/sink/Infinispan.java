package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Properties;


@Buildable
@Getter
@Setter
public class Infinispan implements SinkNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "infinispan";

    private final String type = "infinispan";

    private String infinispanServerHost;

    private Integer infinispanServerPort;

    private String infinispanCache;

    private String infinispanUser;

    private String infinispanPassword;

    private String orgInfinispanClientHotrodRemoteCache;

    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();

        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "type", type);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "infinispan.server.host", infinispanServerHost);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "infinispan.server.port", infinispanServerPort);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "infinispan.cache", infinispanCache);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "infinispan.user", infinispanUser);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "infinispan.password", infinispanPassword);
        propertiesBuilder.put("org.infinispan.client.hotrod.RemoteCache", orgInfinispanClientHotrodRemoteCache);

        return propertiesBuilder.getProperties();
    }
}

