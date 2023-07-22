package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.config.ConnectorConfig;
import io.debezium.server.dist.builder.modules.config.types.CaptureMode;
import io.debezium.server.dist.builder.modules.config.types.MongodbConnectionMode;
import io.debezium.server.dist.builder.modules.config.types.SourceStructVersion;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;

public class MongoSourceNode extends ConnectorConfig implements ModuleNode {
    private final String ARTIFACT_ID = DEBEZIUM_CONNECTOR_PREFIX + "mongo";

    private final String connectorClass = "io.debezium.connector.mongodb.MongoDbConnector";

    private String mongodbConnectionString;

    private MongodbConnectionMode mongodbConnectionMode;

    private String mongodbUser;

    private String mongodbPassword;

    private String mongodbAuthsource;

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
}
