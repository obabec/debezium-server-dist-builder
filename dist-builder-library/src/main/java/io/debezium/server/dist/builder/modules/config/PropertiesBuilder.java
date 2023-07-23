package io.debezium.server.dist.builder.modules.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

    public void putAllWithPrefix(String prefix, Properties p) {
        for (Object key : p.keySet()) {
            properties.put(prefix + key, p.getProperty(key.toString()));
        }
    }

    public void putAllWithPrefix(String prefix, HashMap<String, Object> map) {
        for (String key : map.keySet()) {
            properties.put(prefix + key, map.get(key));
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
