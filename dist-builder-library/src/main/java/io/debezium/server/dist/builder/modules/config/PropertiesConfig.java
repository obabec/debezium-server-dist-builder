package io.debezium.server.dist.builder.modules.config;

import java.util.Properties;

public interface PropertiesConfig extends Config {
    Properties toProperties();
}
