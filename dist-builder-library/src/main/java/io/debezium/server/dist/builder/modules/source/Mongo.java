package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.sources.ConnectorConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.CaptureMode;
import io.debezium.server.dist.builder.modules.config.sources.types.MongodbConnectionMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SourceStructVersion;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

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


    @Override
    public Node buildNode(Document document) {
        return new ModuleDependencyBuilder(document)
                .setGroupId(GROUP_ID)
                .setArtifactId(ARTIFACT_ID)
                .buildDependency();
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        propertiesBuilder.put(debeziumServerSourcePrefix + "connector.class", connectorClass);
        propertiesBuilder.put(debeziumServerSourcePrefix + "mongodb.connection.string", mongodbConnectionString);
        propertiesBuilder.put(debeziumServerSourcePrefix + "mongodb.connection.mode", mongodbConnectionMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "mongodb.user", mongodbUser);
        propertiesBuilder.put(debeziumServerSourcePrefix + "mongodb.password", mongodbPassword);
        propertiesBuilder.put(debeziumServerSourcePrefix + "mongodb.authsource", mongodbAuthSource);
        propertiesBuilder.put(debeziumServerSourcePrefix + "mongodb.ssl.enabled", mongodbSslEnabled.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "mongodb.ssl.invalid.hostname.allowed", mongodbSslInvalidHostnameAllowed.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "collection.include.list", String.join(",", collectionIncludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "collection.exclude.list", String.join(",", collectionIncludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "capture.mode", captureMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "field.exclude.list", String.join(",", fieldExcludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "field.renames", String.join(",", fieldRenames));
        propertiesBuilder.put(debeziumServerSourcePrefix + "mongodb.hosts", String.join(",", mongodbHosts));
        propertiesBuilder.put(debeziumServerSourcePrefix + "poll.interval.ms", pollIntervalMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "connect.backoff.initial.delay.ms", connectBackoffInitialDelayMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "connect.backoff.max.delay.ms", connectBackoffMaxDelayMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "connect.max.attempts", connectMaxAttempts);
        propertiesBuilder.put(debeziumServerSourcePrefix + "source.struct.version", sourceStructVersion.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.collection.filter.overrides", snapshotCollectionFilterOverrides);
        propertiesBuilder.put(debeziumServerSourcePrefix + "retriable.restart.connector.wait.ms", retriableRestartConnectorWaitMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "mongodb.poll.interval.ms", mongodbPollIntervalMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "mongodb.connect.timeout.ms", mongodbConnectTimeoutMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "mongodb.heartbeat.frequency.ms", mongodbHeartbeatFrequencyMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "mongodb.socket.timeout.ms", mongodbSocketTimeoutMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "mongodb.server.selection.timeout.ms", mongodbServerSelectionTimeoutMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "cursor.pipeline", cursorPipeline);
        propertiesBuilder.put(debeziumServerSourcePrefix + "cursor.max.await.time.ms", cursorMaxAwaitTimeMs);
        return propertiesBuilder.getProperties();
    }
}