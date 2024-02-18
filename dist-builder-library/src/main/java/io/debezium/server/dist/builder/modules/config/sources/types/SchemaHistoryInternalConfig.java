package io.debezium.server.dist.builder.modules.config.sources.types;

import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import io.debezium.server.dist.builder.modules.config.YamlConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;


@Getter
@Setter
public class SchemaHistoryInternalConfig implements PropertiesConfig, YamlConfig {

    protected String kafkaTopic;

    protected List<String> kafkaBootstrapServers;

    protected Integer kafkaRecoveryPollIntervalMs;

    protected Integer kafkaQueryTimeoutMs;

    protected Integer kafkaCreateTimeoutMs;

    protected Integer kafkaRecoveryAttempts;

    protected Boolean skipUnparseableDdl;

    protected Boolean storeOnlyCapturedTablesDdl;

    protected Boolean storeOnlyCapturedDatabasesDdl;

    public SchemaHistoryInternalConfig() {
    }

    @Override
    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        String PREFIX = "debezium.source.schema.history.internal";
        builder.put(PREFIX + "kafka.topic", kafkaTopic);
        builder.putList(PREFIX + "kafka.bootstrap.servers", kafkaBootstrapServers);
        builder.put(PREFIX + "kafka.recovery.poll.interval.ms", kafkaRecoveryPollIntervalMs);
        builder.put(PREFIX + "kafka.query.timeout.ms", kafkaQueryTimeoutMs);
        builder.put(PREFIX + "kafka.create.timeout.ms", kafkaCreateTimeoutMs);
        builder.put(PREFIX + "kafka.recovery.attempts", kafkaRecoveryAttempts);
        builder.putBoolean(PREFIX + "skip.unparseable.ddl", skipUnparseableDdl);
        builder.putBoolean(PREFIX + "store.only.captured.ddl", storeOnlyCapturedTablesDdl);
        builder.putBoolean(PREFIX + "store.only.captured.databases.ddl", storeOnlyCapturedDatabasesDdl);
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        getCommonConfig(propertiesBuilder);
        return propertiesBuilder.getProperties();
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getCommonConfig(yamlBuilder);
        return yamlBuilder.getYaml();
    }
}
