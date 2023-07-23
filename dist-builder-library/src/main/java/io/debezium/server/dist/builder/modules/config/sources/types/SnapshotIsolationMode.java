package io.debezium.server.dist.builder.modules.config.sources.types;

public enum SnapshotIsolationMode {
    READ_UNCOMMITTED,
    READ_COMMITTED,
    REPEATABLE_READ,
    SNAPSHOT,
    EXCLUSIVE
}
