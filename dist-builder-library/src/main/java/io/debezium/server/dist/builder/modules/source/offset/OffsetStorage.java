package io.debezium.server.dist.builder.modules.source.offset;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.source.storage.StorageConfig;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Properties;

//TODO: problem melo by to vzdycky byt jdbc.offset.storage atp...
@Getter
@Setter
@Buildable
public class OffsetStorage implements PropertiesConfig, ModuleNode {

    private String offsetStorage;
    private StorageConfig storageConfig;

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        propertiesBuilder.put("debezium.source.offset.storage", offsetStorage);
        propertiesBuilder.putAllWithPrefix("debezium.source.offset.storage.", storageConfig);
        return propertiesBuilder.getProperties();
    }

    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return storageConfig.buildNode(document, dependencyList);
    }
}
