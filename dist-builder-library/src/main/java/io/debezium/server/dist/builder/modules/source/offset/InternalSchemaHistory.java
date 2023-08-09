package io.debezium.server.dist.builder.modules.source.offset;

import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.source.storage.SchemaHistoryStorage;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

//@Buildable
@Getter
@Setter
@Buildable
public class InternalSchemaHistory implements PropertiesConfig {
    private String schemaHistoryClass;
    private SchemaHistoryStorage storageConfig;


    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        propertiesBuilder.put("debezium.source.schema.history.internal", schemaHistoryClass);
        propertiesBuilder.putAllWithPrefix("debezium.source.schema.history.internal.", storageConfig);
        return propertiesBuilder.getProperties();
    }
}
