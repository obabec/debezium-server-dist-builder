package io.debezium.server.dist.builder.modules.config;

import java.util.List;
import java.util.Map;

public interface ConfigBuilder<T extends Config> {
    void put(String key, Object value);

    <E extends Enum<E>> void putEnumWithLowerCase(String key, E e);

    <E extends Enum<E>> void putEnum(String key, E e);

    void putList(String key, List<String> list);

    void putBoolean(String key, Boolean value);

    void putAll(T p);

    void putAllWithPrefix(String prefix, T p);

    void putAllWithPrefix(String prefix, Map<String, Object> map);

}
