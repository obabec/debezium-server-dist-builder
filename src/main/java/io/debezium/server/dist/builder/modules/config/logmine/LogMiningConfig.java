package io.debezium.server.dist.builder.modules.config.logmine;

import java.util.List;

public class LogMiningConfig {
    private final String PREFIX = "log.mining";

    private Strategy strategy;
    private QueryFilterMode queryFilterMode;

    private BufferType bufferType;

    private Integer bufferTransactionEventsThreshold;

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

}