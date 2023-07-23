package io.debezium.server.dist.builder.modules.config.sources.types;

public enum SchemaRefreshMode {
    COLUMNS_DIFF,
    COLUMNS_DIFF_EXCLUDE_UNCHANGED_TOAST
}
