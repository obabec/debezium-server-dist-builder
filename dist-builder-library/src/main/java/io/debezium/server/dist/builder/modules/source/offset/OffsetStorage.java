package io.debezium.server.dist.builder.modules.source.offset;

import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.source.storage.StorageConfig;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

//@Buildable
@Getter
@Setter
@Buildable
public class OffsetStorage implements PropertiesConfig {

    private String offsetStorage;
    private StorageConfig storageConfig;

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        propertiesBuilder.put("debezium.source.offset.storage", offsetStorage);
        propertiesBuilder.putAllWithPrefix("debezium.source.offset.storage.", storageConfig);
        return propertiesBuilder.getProperties();
    }
}
