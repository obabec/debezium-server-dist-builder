package io.debezium.server.dist.builder.modules.config;

import java.util.Properties;

public class PropertiesBuilder {
    private Properties properties;

    public PropertiesBuilder(Properties properties) {
        this.properties = properties;
    }

    public PropertiesBuilder() {
        this.properties = new Properties();
    }

    public void put(Object key, Object value) {
        if (value != null) {
            properties.put(key, value);
        }
    }

    public void putAll(Properties p) {
        properties.putAll(p);
    }

    public Properties getProperties() {
        return properties;
    }
}
