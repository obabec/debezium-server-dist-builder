package io.debezium.server.dist.builder.modules.config;

public interface Config {
    <C extends Config> void getCommonConfig(ConfigBuilder<C> builder);
}
