package io.debezium.server.dist.builder.modules.config.sources.types;

import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Properties;


@Getter
@Setter
public class SchemaHistoryInternalConfig implements PropertiesConfig {

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
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        String PREFIX = "debezium.source.schema.history.internal";
        propertiesBuilder.put(PREFIX + "kafka.topic", kafkaTopic);
        propertiesBuilder.putList(PREFIX + "kafka.bootstrap.servers", kafkaBootstrapServers);
        propertiesBuilder.put(PREFIX + "kafka.recovery.poll.interval.ms", kafkaRecoveryPollIntervalMs);
        propertiesBuilder.put(PREFIX + "kafka.query.timeout.ms", kafkaQueryTimeoutMs);
        propertiesBuilder.put(PREFIX + "kafka.create.timeout.ms", kafkaCreateTimeoutMs);
        propertiesBuilder.put(PREFIX + "kafka.recovery.attempts", kafkaRecoveryAttempts);
        propertiesBuilder.putBoolean(PREFIX + "skip.unparseable.ddl", skipUnparseableDdl);
        propertiesBuilder.putBoolean(PREFIX + "store.only.captured.ddl", storeOnlyCapturedTablesDdl);
        propertiesBuilder.putBoolean(PREFIX + "store.only.captured.databases.ddl", storeOnlyCapturedDatabasesDdl);
        return propertiesBuilder.getProperties();
    }
}
