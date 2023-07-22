package io.debezium.server.dist.builder.modules.config.types;

public enum SnapshotMode {
    INITIAL,
    INITIAL_ONLY,
    WHEN_NEEDED,
    NEVER,
    SCHEMA_ONLY,
    SCHEMA_ONLY_RECOVERY
}
