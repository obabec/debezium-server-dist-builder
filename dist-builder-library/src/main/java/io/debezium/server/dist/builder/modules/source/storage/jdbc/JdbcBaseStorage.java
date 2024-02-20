package io.debezium.server.dist.builder.modules.source.storage.jdbc;

import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import io.debezium.server.dist.builder.modules.config.YamlConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Properties;

@Getter
@Setter
public class JdbcBaseStorage implements PropertiesConfig, YamlConfig {
    protected final String ARTIFACT_ID = "debezium-storage-jdbc";

    String url;
    String username;
    String password;

    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put("jdbc.url", url);
        builder.put("jdbc.username", username);
        builder.put("jdbc.password", password);
    }
    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        getCommonConfig(propertiesBuilder);
        return propertiesBuilder.getProperties();
    }



    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getCommonConfig(yamlBuilder);
        return yamlBuilder.getYaml();
    }
}
