package io.debezium.server.dist.builder.modules.config.sources.logmine;

import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Properties;


@Getter
@Setter
public class LogMiningConfig implements PropertiesConfig {

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
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        final String PREFIX = "debezium.source.log.mining.";
        propertiesBuilder.put(PREFIX + "strategy", strategy.toString().toLowerCase());
        propertiesBuilder.put(PREFIX + "query.filter.mode", queryFilterMode.toString().toLowerCase());
        propertiesBuilder.put(PREFIX + "buffer.type", bufferType.toString().toLowerCase());
        propertiesBuilder.put(PREFIX + "buffer.transaction.events.threshold", bufferTransactionEventsThreshold);
        propertiesBuilder.put(PREFIX + "buffer.infinispan.cache.transactions", bufferInfinispanCacheTransactions);
        propertiesBuilder.put(PREFIX + "buffer.infinispan.cache.events", bufferInfinispanCacheEvents);
        propertiesBuilder.put(PREFIX + "buffer.infinispan.cache.processed_transactions", bufferInfinispanCacheProcessedTransactions);
        propertiesBuilder.put(PREFIX + "buffer.infinispan.cache.schema_changes", bufferInfinispanCacheSchemaChanges);
        propertiesBuilder.put(PREFIX + "buffer.drop.on.stop", bufferDropOnStop.toString());
        propertiesBuilder.put(PREFIX + "session.max.ms", sessionMaxMs);
        propertiesBuilder.put(PREFIX + "restart.connection", restartConnection);
        propertiesBuilder.put(PREFIX + "batch.size.min", batchSizeMin);
        propertiesBuilder.put(PREFIX + "batch.size.max", batchSizeMax);
        propertiesBuilder.put(PREFIX + "batch.size.default", batchSizeDefault);
        propertiesBuilder.put(PREFIX + "sleep.time.min.ms", sleepTimeMinMs);
        propertiesBuilder.put(PREFIX + "sleep.time.max.ms", sleepTimeMaxMs);
        propertiesBuilder.put(PREFIX + "sleep.time.default.ms", sleepTimeDefaultMs);
        propertiesBuilder.put(PREFIX + "sleep.time.increment.ms", sleepTimeIncrementMs);
        propertiesBuilder.put(PREFIX + "archive.log.hours", archiveLogHours);
        propertiesBuilder.put(PREFIX + "archive.log.only.mode", archiveLogOnlyMode);
        propertiesBuilder.put(PREFIX + "archive.log.only.scn.poll.interval.ms", archiveLogOnlyScnPollIntervalMs);
        propertiesBuilder.put(PREFIX + "transaction.retention.ms", transactionRetentionMs);
        propertiesBuilder.put(PREFIX + "archive.destination.name", archiveDestinationName);
        propertiesBuilder.put(PREFIX + "username.include.list", String.join(",", usernameIncludeList));
        propertiesBuilder.put(PREFIX + "username.exclude.list", String.join(",", usernameExcludeList));
        propertiesBuilder.put(PREFIX + "scn.gap.detection.gap.size.min", scnGapDetectionGapSizeMin);
        propertiesBuilder.put(PREFIX + "scn.gap.detection.time.interval.max.ms", scnGapDetectionTimeIntervalMaxMs);
        propertiesBuilder.put(PREFIX + "flush.table.name", flushTableName);
        return propertiesBuilder.getProperties();
    }
}