package io.debezium.server.dist.builder.modules.config.sources.types;

public enum DatabaseSslMode {
    DISABLE,
    ALLOW,
    PREFER,
    REQUIRE,
    VERIFY_CA,
    VERIFY_FULL
}
