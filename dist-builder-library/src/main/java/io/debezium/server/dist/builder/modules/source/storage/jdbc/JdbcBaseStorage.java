package io.debezium.server.dist.builder.modules.source.storage.jdbc;

import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

@Getter
@Setter
public class JdbcBaseStorage implements PropertiesConfig {
    protected final String ARTIFACT_ID = "debezium-storage-jdbc";

    String url;
    String username;
    String password;

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        propertiesBuilder.put("jdbc.url", url);
        propertiesBuilder.put("jdbc.username", username);
        propertiesBuilder.put("jdbc.password", password);
        return propertiesBuilder.getProperties();
    }
}
