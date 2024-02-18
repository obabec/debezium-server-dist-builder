package io.debezium.server.dist.builder.modules.config.sources.logmine;

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
public class LogMiningConfig implements PropertiesConfig, YamlConfig {

    private Strategy strategy;
    private QueryFilterMode queryFilterMode;

    private BufferType bufferType;

    private Integer bufferTransactionEventsThreshold;

    private String bufferInfinispanCacheTransactions;

    private String bufferInfinispanCacheEvents;

    // name of the property log.mining.buffer.infinispan.cache.processed_transactions
    private String bufferInfinispanCacheProcessedTransactions;

    private String bufferInfinispanCacheSchemaChanges;

    private Boolean bufferDropOnStop;

    private Integer sessionMaxMs;

    private Boolean restartConnection;

    private Integer batchSizeMin;
    private Integer batchSizeMax;

    private Integer batchSizeDefault;

    private Integer sleepTimeMinMs;
    private Integer sleepTimeMaxMs;
    private Integer sleepTimeDefaultMs;
    private Integer sleepTimeIncrementMs;
    private Integer archiveLogHours;

    private Boolean archiveLogOnlyMode;

    private Integer archiveLogOnlyScnPollIntervalMs;

    private Integer transactionRetentionMs;

    private String archiveDestinationName;

    private List<String> usernameIncludeList;

    private List<String> usernameExcludeList;

    private Integer scnGapDetectionGapSizeMin;
    private Integer scnGapDetectionTimeIntervalMaxMs;

    private String flushTableName;

    @Override
    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        final String PREFIX = "debezium.source.log.mining.";
        builder.putEnumWithLowerCase(PREFIX + "strategy", strategy);
        builder.putEnumWithLowerCase(PREFIX + "query.filter.mode", queryFilterMode);
        builder.putEnumWithLowerCase(PREFIX + "buffer.type", bufferType);
        builder.put(PREFIX + "buffer.transaction.events.threshold", bufferTransactionEventsThreshold);
        builder.put(PREFIX + "buffer.infinispan.cache.transactions", bufferInfinispanCacheTransactions);
        builder.put(PREFIX + "buffer.infinispan.cache.events", bufferInfinispanCacheEvents);
        builder.put(PREFIX + "buffer.infinispan.cache.processed_transactions", bufferInfinispanCacheProcessedTransactions);
        builder.put(PREFIX + "buffer.infinispan.cache.schema_changes", bufferInfinispanCacheSchemaChanges);
        builder.putBoolean(PREFIX + "buffer.drop.on.stop", bufferDropOnStop);
        builder.put(PREFIX + "session.max.ms", sessionMaxMs);
        builder.put(PREFIX + "restart.connection", restartConnection);
        builder.put(PREFIX + "batch.size.min", batchSizeMin);
        builder.put(PREFIX + "batch.size.max", batchSizeMax);
        builder.put(PREFIX + "batch.size.default", batchSizeDefault);
        builder.put(PREFIX + "sleep.time.min.ms", sleepTimeMinMs);
        builder.put(PREFIX + "sleep.time.max.ms", sleepTimeMaxMs);
        builder.put(PREFIX + "sleep.time.default.ms", sleepTimeDefaultMs);
        builder.put(PREFIX + "sleep.time.increment.ms", sleepTimeIncrementMs);
        builder.put(PREFIX + "archive.log.hours", archiveLogHours);
        builder.put(PREFIX + "archive.log.only.mode", archiveLogOnlyMode);
        builder.put(PREFIX + "archive.log.only.scn.poll.interval.ms", archiveLogOnlyScnPollIntervalMs);
        builder.put(PREFIX + "transaction.retention.ms", transactionRetentionMs);
        builder.put(PREFIX + "archive.destination.name", archiveDestinationName);
        builder.putList(PREFIX + "username.include.list", usernameIncludeList);
        builder.put(PREFIX + "username.exclude.list", usernameExcludeList);
        builder.put(PREFIX + "scn.gap.detection.gap.size.min", scnGapDetectionGapSizeMin);
        builder.put(PREFIX + "scn.gap.detection.time.interval.max.ms", scnGapDetectionTimeIntervalMaxMs);
        builder.put(PREFIX + "flush.table.name", flushTableName);
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