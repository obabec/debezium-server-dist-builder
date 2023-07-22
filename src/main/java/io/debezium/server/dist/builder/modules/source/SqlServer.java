package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.config.sources.SqlBasedConnectorConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.SchemaHistoryInternalConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.SnapshotIsolationMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SourceStructVersion;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;

public class SqlServer extends SqlBasedConnectorConfig implements ModuleNode {
    private final String ARTIFACT_ID = DEBEZIUM_CONNECTOR_PREFIX + "sql";
    private final String connectorClass = "io.debezium.connector.sqlserver.SqlServerConnector";
    private String databaseInstance;

    private List<String> databaseNames;

    private List<String> schemaIncludeList;
    private List<String> schemaExcludeList;

    private Boolean skipMessagesWithoutChange;

    private String snapshotLockingMode;

    private SnapshotIsolationMode snapshotIsolationMode;

    private Integer pollIntervalMs;

    private Integer queryFetchSize;

    private SourceStructVersion sourceStructVersion;

    private Integer retriableRestartConnectorWaitMs;

    private Boolean incrementalSnapshotAllowSchemaChanges;

    private Integer maxIterationTransaction;

    private Boolean incrementalSnapshotOptionRecompile;

    private HashMap<String, String> customMetricTags;
    private SchemaHistoryInternalConfig schemaHistoryInternalConfig;
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
