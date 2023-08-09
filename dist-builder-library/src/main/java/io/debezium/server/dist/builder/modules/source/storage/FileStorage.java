package io.debezium.server.dist.builder.modules.source.storage;

import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;


@Buildable
@Getter
@Setter
public class FileStorage implements StorageConfig {

    private String fileFilename;
    private Integer flushIntervalMs;

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        propertiesBuilder.put("file.filename", fileFilename);
        propertiesBuilder.put("internal.ms", flushIntervalMs);
        return propertiesBuilder.getProperties();
    }
}
