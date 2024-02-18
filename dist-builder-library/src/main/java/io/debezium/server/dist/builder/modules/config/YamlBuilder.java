package io.debezium.server.dist.builder.modules.config;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Getter
public class YamlBuilder implements ConfigBuilder<YamlConfig> {
    private final HashMap<String, Object> yaml;

    public YamlBuilder(HashMap<String, Object> yaml) {
        this.yaml = yaml;
    }

    public YamlBuilder() {
        this.yaml = new HashMap<>();
    }

    @Override
    public void put(String key, Object value) {
        if (value != null) {
         yaml.put(key, value);
        }
    }

    @Override
    public <E extends Enum<E>> void putEnumWithLowerCase(String key, E e) {
        if (e != null) {
            yaml.put(key, e.toString().toLowerCase());
        }
    }

    @Override
    public <E extends Enum<E>> void putEnum(String key, E e) {
        if (e != null) {
            yaml.put(key, e.toString());
        }
    }

    @Override
    public void putList(String key, List<String> list) {
        if (list != null && !list.isEmpty()) {
            yaml.put(key, String.join(",", list));
        }
    }

    @Override
    public void putBoolean(String key, Boolean value) {
        if (value != null) {
            yaml.put(key, value.toString());
        }
    }

    @Override
    public void putAll(YamlConfig p) {
        if (p != null) {
            yaml.putAll(p.toYaml());
        }
    }

    @Override
    public void putAllWithPrefix(String prefix, YamlConfig p) {
        HashMap<String, Object> objectHashMap = p.toYaml();
        if (objectHashMap != null) {
            for (Map.Entry<String, Object> entry : objectHashMap.entrySet()) {
                yaml.put(prefix + entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void putAllWithPrefix(String prefix, HashMap<String, Object> map) {
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                yaml.put(prefix + entry.getKey(), entry.getValue());
            }
        }
    }

    public void putAllWithKey(String key, YamlConfig p) {
        if (p != null) {
            yaml.put(key, p.toYaml());
        }
    }

    public void putAllWithKeyAndPrefix(String key, String prefix, YamlConfig p) {
        Map<String, Object> newMap = new HashMap<>();
        Map<String, Object> objectHashMap = p.toYaml();
        if (objectHashMap != null) {
            for (Map.Entry<String, Object> entry : objectHashMap.entrySet()) {
                newMap.put(prefix + entry.getKey(), entry.getValue());
            }
        }
        yaml.put(key, newMap);
    }
}
