package io.debezium.server.dist.builder.modules.config.sources;

import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.NameAdjustmentMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SignalConfiguration;
import io.debezium.server.dist.builder.modules.config.sources.types.SnapshotMode;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Properties;

@Buildable
@Getter
@Setter
public class ConnectorConfig implements PropertiesConfig {
    protected final String debeziumServerSourcePrefix = "debezium.source.";

    public ConnectorConfig() {
    }

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

    protected String notificationsSinkTopicName;


    @Override
    public Properties toProperties(){
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        propertiesBuilder.put(debeziumServerSourcePrefix + "name", this.name);
        propertiesBuilder.put(debeziumServerSourcePrefix + "topic.prefix", this.topicPrefix);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.include.list", String.join(",", databaseIncludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.exclude.list", String.join(",", databaseExcludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.mode", snapshotMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.include.collection.list", String.join(",", snapshotIncludeCollectionList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "tasks.max", tasksMax);
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.max.threads", snapshotMaxThreads);
        propertiesBuilder.put(debeziumServerSourcePrefix + "tombstones.on.delete", tombstonesOnDelete.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.delay.ms", snapshotDelayMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.fetch.size", snapshotFetchSize);
        propertiesBuilder.put(debeziumServerSourcePrefix + "schema.name.adjustment.mode", schemaNameAdjustmentMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "field.name.adjustment.mode", fieldNameAdjustmentMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "max.batch.size", maxBatchSize);
        propertiesBuilder.put(debeziumServerSourcePrefix + "max.queue.size", maxQueueSize);
        propertiesBuilder.put(debeziumServerSourcePrefix + "max.queue.size.in.bytes", maxQueueSizeInBytes);
        propertiesBuilder.put(debeziumServerSourcePrefix + "heartbeat.interval.ms", heartbeatIntervalMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "skipped.operations", skippedOperations);
        propertiesBuilder.put(debeziumServerSourcePrefix + "provide.transaction.metadata", provideTransactionMetadata);
        propertiesBuilder.put(debeziumServerSourcePrefix + "signal.data.collection", signalDataCollection);
        propertiesBuilder.put(debeziumServerSourcePrefix + "signal.enabled.channels", String.join(",", signalEnabledChannels));
        propertiesBuilder.put(debeziumServerSourcePrefix + "notifications.enabled.channels", String.join(",", notificationsEnabledChannels));
        propertiesBuilder.put(debeziumServerSourcePrefix + "incremental.snapshot.chunk.size", incrementalSnapshotChunkSize);
        propertiesBuilder.put(debeziumServerSourcePrefix + "topic.naming.strategy", topicNamingStrategy);
        propertiesBuilder.put(debeziumServerSourcePrefix + "topic.delimiter", topicDelimiter);
        propertiesBuilder.put(debeziumServerSourcePrefix + "topic.cache.size", topicCacheSize);
        propertiesBuilder.put(debeziumServerSourcePrefix + "topic.heartbeat.prefix", topicHeartbeatPrefix);
        propertiesBuilder.put(debeziumServerSourcePrefix + "topic.transaction", topicTransaction);
        if (signalConfiguration != null) {
            propertiesBuilder.putAll(signalConfiguration.toProperties());
        }
        propertiesBuilder.put(debeziumServerSourcePrefix + "notifications.sink.topic.name", notificationsSinkTopicName);
        return propertiesBuilder.getProperties();
    }
}
