package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import io.debezium.server.dist.builder.modules.config.sources.ConnectorConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.CaptureMode;
import io.debezium.server.dist.builder.modules.config.sources.types.MongodbConnectionMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SourceStructVersion;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@Buildable
@Getter
@Setter
public class Mongo extends ConnectorConfig implements SourceNode {
    private final String ARTIFACT_ID = DEBEZIUM_CONNECTOR_PREFIX + "mongo";

    private final String connectorClass = "io.debezium.connector.mongodb.MongoDbConnector";

    private String mongodbConnectionString;

    private MongodbConnectionMode mongodbConnectionMode;

    private String mongodbUser;

    private String mongodbPassword;

    private String mongodbAuthSource;

    private Boolean mongodbSslEnabled;

    private Boolean mongodbSslInvalidHostnameAllowed;

    private List<String> collectionIncludeList;

    private List<String> collectionExcludeList;

    private CaptureMode captureMode;

    private List<String> fieldExcludeList;

    private List<String> fieldRenames;

    private List<String> mongodbHosts;

    private Integer pollIntervalMs;

    private Integer connectBackoffInitialDelayMs;

    private Integer connectBackoffMaxDelayMs;

    private Integer connectMaxAttempts;

    private SourceStructVersion sourceStructVersion;

    private String snapshotCollectionFilterOverrides;

    private Integer retriableRestartConnectorWaitMs;

    private Integer mongodbPollIntervalMs;

    private Integer mongodbConnectTimeoutMs;

    private Integer mongodbHeartbeatFrequencyMs;

    private Integer mongodbSocketTimeoutMs;

    private Integer mongodbServerSelectionTimeoutMs;

    private String cursorPipeline;

    private String cursorMaxAwaitTimeMs;

    public Mongo() {
    }

    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    @Override
    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put(debeziumServerSourcePrefix + "mongodb.connection.string", mongodbConnectionString);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "mongodb.connection.mode", mongodbConnectionMode);
        builder.put(debeziumServerSourcePrefix + "mongodb.user", mongodbUser);
        builder.put(debeziumServerSourcePrefix + "mongodb.password", mongodbPassword);
        builder.put(debeziumServerSourcePrefix + "mongodb.authsource", mongodbAuthSource);
        builder.putBoolean(debeziumServerSourcePrefix + "mongodb.ssl.enabled", mongodbSslEnabled);
        builder.putBoolean(debeziumServerSourcePrefix + "mongodb.ssl.invalid.hostname.allowed", mongodbSslInvalidHostnameAllowed);
        builder.putList(debeziumServerSourcePrefix + "collection.include.list", collectionIncludeList);
        builder.putList(debeziumServerSourcePrefix + "collection.exclude.list", collectionIncludeList);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "capture.mode", captureMode);
        builder.putList(debeziumServerSourcePrefix + "field.exclude.list", fieldExcludeList);
        builder.putList(debeziumServerSourcePrefix + "field.renames", fieldRenames);
        builder.putList(debeziumServerSourcePrefix + "mongodb.hosts", mongodbHosts);
        builder.put(debeziumServerSourcePrefix + "poll.interval.ms", pollIntervalMs);
        builder.put(debeziumServerSourcePrefix + "connect.backoff.initial.delay.ms", connectBackoffInitialDelayMs);
        builder.put(debeziumServerSourcePrefix + "connect.backoff.max.delay.ms", connectBackoffMaxDelayMs);
        builder.put(debeziumServerSourcePrefix + "connect.max.attempts", connectMaxAttempts);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "source.struct.version", sourceStructVersion);
        builder.put(debeziumServerSourcePrefix + "snapshot.collection.filter.overrides", snapshotCollectionFilterOverrides);
        builder.put(debeziumServerSourcePrefix + "retriable.restart.connector.wait.ms", retriableRestartConnectorWaitMs);
        builder.put(debeziumServerSourcePrefix + "mongodb.poll.interval.ms", mongodbPollIntervalMs);
        builder.put(debeziumServerSourcePrefix + "mongodb.connect.timeout.ms", mongodbConnectTimeoutMs);
        builder.put(debeziumServerSourcePrefix + "mongodb.heartbeat.frequency.ms", mongodbHeartbeatFrequencyMs);
        builder.put(debeziumServerSourcePrefix + "mongodb.socket.timeout.ms", mongodbSocketTimeoutMs);
        builder.put(debeziumServerSourcePrefix + "mongodb.server.selection.timeout.ms", mongodbServerSelectionTimeoutMs);
        builder.put(debeziumServerSourcePrefix + "cursor.pipeline", cursorPipeline);
        builder.put(debeziumServerSourcePrefix + "cursor.max.await.time.ms", cursorMaxAwaitTimeMs);
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getCommonConfig(yamlBuilder);
        return yamlBuilder.getYaml();
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        propertiesBuilder.put(debeziumServerSourcePrefix + "connector.class", connectorClass);
        getCommonConfig(propertiesBuilder);
        return propertiesBuilder.getProperties();
    }
}
