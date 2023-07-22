package io.debezium.server.dist.builder.modules.config.sources;

import io.debezium.server.dist.builder.modules.config.sources.types.NameAdjustmentMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SignalConfiguration;
import io.debezium.server.dist.builder.modules.config.sources.types.SnapshotMode;

import java.util.List;

public class ConnectorConfig {
    protected final String debeziumServerSourcePrefix = "debezium.source";

    protected String name;
    protected String topicPrefix;

    protected List<String> databaseIncludeList;
    protected List<String> databaseExcludeList;

    protected SnapshotMode snapshotMode;

    protected List<String> snapshotIncludeCollectionList;

    protected Integer tasksMax;

    protected Integer snapshotMaxThreads;

    protected Boolean tombstoneOnDelete;

    protected Integer snapshotDelayMs;

    protected Integer snapshotFetchSize;

    protected NameAdjustmentMode schemaNameAdjustmentMode;

    protected NameAdjustmentMode fieldNameAdjustmentMode;

    protected Integer maxBatchSize;

    protected Integer maxQueueSize;

    protected Long maxQueueSizeInBytes;

    protected Integer heartbeatIntervalMs;

    protected char skippedOperations = 't';

    protected Boolean provideTransactionMetadata;

    protected String signalDataCollection;

    protected List<String> signalEnabledChannels;

    protected List<String> notificationsEnabledChannels;

    protected Integer incrementalSnapshotChunkSize;

    protected String topicNamingStrategy;
    protected String topicDelimiter;
    protected Integer topicCacheSize;

    protected String topicHeartbeatPrefix;

    protected String topicTransaction;

    protected SignalConfiguration signalConfiguration;

    protected String notificationsSingTopicName;
}
