package io.debezium.server.dist.builder.modules.config.types;

import java.util.List;

public class SchemaHistoryInternalConfig {
    private final String PREFIX = "schema.history.internal";

    protected String kafkaTopic;

    protected List<String> kafkaBootstrapServers;

    protected Integer kafkaRecoveryPollInternalMs;

    protected Integer kafkaQueryTimeoutMs;

    protected Integer kafkaCreateTimeoutMs;

    protected Integer kafkaRecoveryAttempts;

    protected Boolean skipUnparseableDdl;

    protected Boolean storeOnlyCapturedTablesDdl;

    protected Boolean storeOnlyCapturedDatabasesDdl;
}
