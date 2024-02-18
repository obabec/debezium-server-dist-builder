package io.debezium.server.dist.builder.modules.source.storage;

import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.config.YamlConfig;

public interface SchemaHistoryStorage extends PropertiesConfig, YamlConfig, ModuleNode {
}
