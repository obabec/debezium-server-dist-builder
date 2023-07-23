package io.debezium.server.dist.builder.modules.config.sources.types;

import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Properties;

@Buildable
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
        propertiesBuilder.put(PREFIX + "kafka.bootstrap.servers", String.join(",", kafkaBootstrapServers));
        propertiesBuilder.put(PREFIX + "kafka.recovery.poll.interval.ms", kafkaRecoveryPollIntervalMs);
        propertiesBuilder.put(PREFIX + "kafka.query.timeout.ms", kafkaQueryTimeoutMs);
        propertiesBuilder.put(PREFIX + "kafka.create.timeout.ms", kafkaCreateTimeoutMs);
        propertiesBuilder.put(PREFIX + "kafka.recovery.attempts", kafkaRecoveryAttempts);
        propertiesBuilder.put(PREFIX + "skip.unparseable.ddl", skipUnparseableDdl.toString());
        propertiesBuilder.put(PREFIX + "store.only.captured.ddl", storeOnlyCapturedTablesDdl.toString());
        propertiesBuilder.put(PREFIX + "store.only.captured.databases.ddl", storeOnlyCapturedDatabasesDdl.toString());
        return propertiesBuilder.getProperties();
    }
}
