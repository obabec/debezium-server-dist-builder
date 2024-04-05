package io.debezium.server.dist.builder.modules.config.sources;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import io.debezium.server.dist.builder.modules.config.YamlConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.NameAdjustmentMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SignalConfiguration;
import io.debezium.server.dist.builder.modules.config.sources.types.SnapshotMode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ConnectorConfig implements PropertiesConfig, YamlConfig {
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
        getConnectorCommonConfig(propertiesBuilder);
        propertiesBuilder.putAll(signalConfiguration);
        return propertiesBuilder.getProperties();
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getConnectorCommonConfig(yamlBuilder);
        yamlBuilder.putAll(signalConfiguration);
        return yamlBuilder.getYaml();
    }

    public <C extends Config> void getConnectorCommonConfig(ConfigBuilder<C> builder) {
        builder.put(debeziumServerSourcePrefix + "name", this.name);
        builder.put(debeziumServerSourcePrefix + "topic.prefix", this.topicPrefix);
        builder.putList(debeziumServerSourcePrefix + "database.include.list", databaseIncludeList);
        builder.putList(debeziumServerSourcePrefix + "database.exclude.list", databaseExcludeList);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "snapshot.mode", snapshotMode);
        builder.putList(debeziumServerSourcePrefix + "snapshot.include.collection.list", snapshotIncludeCollectionList);
        builder.put(debeziumServerSourcePrefix + "tasks.max", tasksMax);
        builder.put(debeziumServerSourcePrefix + "snapshot.max.threads", snapshotMaxThreads);
        builder.putBoolean(debeziumServerSourcePrefix + "tombstones.on.delete", tombstonesOnDelete);
        builder.put(debeziumServerSourcePrefix + "snapshot.delay.ms", snapshotDelayMs);
        builder.put(debeziumServerSourcePrefix + "snapshot.fetch.size", snapshotFetchSize);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "schema.name.adjustment.mode", schemaNameAdjustmentMode);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "field.name.adjustment.mode", fieldNameAdjustmentMode);
        builder.put(debeziumServerSourcePrefix + "max.batch.size", maxBatchSize);
        builder.put(debeziumServerSourcePrefix + "max.queue.size", maxQueueSize);
        builder.put(debeziumServerSourcePrefix + "max.queue.size.in.bytes", maxQueueSizeInBytes);
        builder.put(debeziumServerSourcePrefix + "heartbeat.interval.ms", heartbeatIntervalMs);
        builder.put(debeziumServerSourcePrefix + "skipped.operations", skippedOperations);
        builder.put(debeziumServerSourcePrefix + "provide.transaction.metadata", provideTransactionMetadata);
        builder.put(debeziumServerSourcePrefix + "signal.data.collection", signalDataCollection);
        builder.putList(debeziumServerSourcePrefix + "signal.enabled.channels", signalEnabledChannels);
        builder.putList(debeziumServerSourcePrefix + "notifications.enabled.channels", notificationsEnabledChannels);
        builder.put(debeziumServerSourcePrefix + "incremental.snapshot.chunk.size", incrementalSnapshotChunkSize);
        builder.put(debeziumServerSourcePrefix + "topic.naming.strategy", topicNamingStrategy);
        builder.put(debeziumServerSourcePrefix + "topic.delimiter", topicDelimiter);
        builder.put(debeziumServerSourcePrefix + "topic.cache.size", topicCacheSize);
        builder.put(debeziumServerSourcePrefix + "topic.heartbeat.prefix", topicHeartbeatPrefix);
        builder.put(debeziumServerSourcePrefix + "topic.transaction", topicTransaction);
        builder.put(debeziumServerSourcePrefix + "notifications.sink.topic.name", notificationsSinkTopicName);

    }
}
