package io.debezium.server.dist.builder.modules.config;

import java.util.HashMap;

public interface YamlConfig extends Config {
    HashMap<String, Object> toYaml();
}
