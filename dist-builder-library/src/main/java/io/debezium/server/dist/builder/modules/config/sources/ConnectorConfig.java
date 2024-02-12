package io.debezium.server.dist.builder.modules.config.sources;

import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.NameAdjustmentMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SignalConfiguration;
import io.debezium.server.dist.builder.modules.config.sources.types.SnapshotMode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Properties;


@Getter
@Setter
public class ConnectorConfig implements PropertiesConfig {
    protected final String debeziumServerSourcePrefix = "debezium.source.";
    protected String name;
    protected String topicPrefix;
    protected List<String> databaseIncludeList;
    protected List<String> databaseExcludeList;
    protected SnapshotMode snapshotMode;
    protected List<String> snapshotIncludeCollectionList;
    protected Integer tasksMax;
    protected Integer snapshotMaxThreads;
    protected Boolean tombstonesOnDelete;
    protected Integer snapshotDelayMs;
    protected Integer snapshotFetchSize;
    protected NameAdjustmentMode schemaNameAdjustmentMode;
    protected NameAdjustmentMode fieldNameAdjustmentMode;
    protected Integer maxBatchSize;
    protected Integer maxQueueSize;
    protected Long maxQueueSizeInBytes;
    protected Integer heartbeatIntervalMs;
    protected String skippedOperations;
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
    protected String notificationsSinkTopicName;
    protected SignalConfiguration signalConfiguration;

    public ConnectorConfig() {
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        propertiesBuilder.put(debeziumServerSourcePrefix + "name", this.name);
        propertiesBuilder.put(debeziumServerSourcePrefix + "topic.prefix", this.topicPrefix);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "database.include.list", databaseIncludeList);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "database.exclude.list", databaseExcludeList);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "snapshot.mode", snapshotMode);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "snapshot.include.collection.list", snapshotIncludeCollectionList);
        propertiesBuilder.put(debeziumServerSourcePrefix + "tasks.max", tasksMax);
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.max.threads", snapshotMaxThreads);
        propertiesBuilder.putBoolean(debeziumServerSourcePrefix + "tombstones.on.delete", tombstonesOnDelete);
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.delay.ms", snapshotDelayMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.fetch.size", snapshotFetchSize);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "schema.name.adjustment.mode", schemaNameAdjustmentMode);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "field.name.adjustment.mode", fieldNameAdjustmentMode);
        propertiesBuilder.put(debeziumServerSourcePrefix + "max.batch.size", maxBatchSize);
        propertiesBuilder.put(debeziumServerSourcePrefix + "max.queue.size", maxQueueSize);
        propertiesBuilder.put(debeziumServerSourcePrefix + "max.queue.size.in.bytes", maxQueueSizeInBytes);
        propertiesBuilder.put(debeziumServerSourcePrefix + "heartbeat.interval.ms", heartbeatIntervalMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "skipped.operations", skippedOperations);
        propertiesBuilder.put(debeziumServerSourcePrefix + "provide.transaction.metadata", provideTransactionMetadata);
        propertiesBuilder.put(debeziumServerSourcePrefix + "signal.data.collection", signalDataCollection);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "signal.enabled.channels", signalEnabledChannels);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "notifications.enabled.channels", notificationsEnabledChannels);
        propertiesBuilder.put(debeziumServerSourcePrefix + "incremental.snapshot.chunk.size", incrementalSnapshotChunkSize);
        propertiesBuilder.put(debeziumServerSourcePrefix + "topic.naming.strategy", topicNamingStrategy);
        propertiesBuilder.put(debeziumServerSourcePrefix + "topic.delimiter", topicDelimiter);
        propertiesBuilder.put(debeziumServerSourcePrefix + "topic.cache.size", topicCacheSize);
        propertiesBuilder.put(debeziumServerSourcePrefix + "topic.heartbeat.prefix", topicHeartbeatPrefix);
        propertiesBuilder.put(debeziumServerSourcePrefix + "topic.transaction", topicTransaction);
        propertiesBuilder.putAll(signalConfiguration);
        propertiesBuilder.put(debeziumServerSourcePrefix + "notifications.sink.topic.name", notificationsSinkTopicName);
        return propertiesBuilder.getProperties();
    }
}
