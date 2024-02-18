package io.debezium.server.dist.builder.modules.config;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@Getter
public class PropertiesBuilder implements ConfigBuilder<PropertiesConfig> {
    private final Properties properties;

    public PropertiesBuilder(Properties properties) {
        this.properties = properties;
    }

    public PropertiesBuilder() {
        this.properties = new Properties();
    }

    @Override
    public void put(String key, Object value) {
        if (value != null) {
            properties.put(key, value);
        }
    }

    @Override
    public <E extends Enum<E>> void putEnumWithLowerCase(String key, E e) {
        if (e != null) {
            properties.put(key, e.toString().toLowerCase());
        }
    }

    @Override
    public <E extends Enum<E>> void putEnum(String key, E e) {
        if (e != null) {
            properties.put(key, e.toString());
        }
    }

    @Override
    public void putList(String key, List<String> list) {
        if (list != null && !list.isEmpty()) {
            properties.put(key, String.join(",", list));
        }
    }

    @Override
    public void putBoolean(String key, Boolean value) {
        if (value != null) {
            properties.put(key, value);
        }
    }

    @Override
    public void putAll(PropertiesConfig p) {
        if (p != null) {
            properties.putAll(p.toProperties());
        }
    }

    @Override
    public void putAllWithPrefix(String prefix, PropertiesConfig p) {
        if (p != null) {
            Properties props = p.toProperties();
            for (Object key : props.keySet()) {
                properties.put(prefix + key, props.get(key));
            }
        }
    }

    @Override
    public void putAllWithPrefix(String prefix, HashMap<String, Object> map) {
        if (map != null) {
            for (String key : map.keySet()) {
                properties.put(prefix + key, map.get(key));
            }
        }
    }
}
