package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.config.SqlBasedConnectorConfig;
import io.debezium.server.dist.builder.modules.config.types.BigintHandlingMode;
import io.debezium.server.dist.builder.modules.config.types.DatabaseSslMode;
import io.debezium.server.dist.builder.modules.config.types.FailureHandlingMode;
import io.debezium.server.dist.builder.modules.config.types.LockingMode;
import io.debezium.server.dist.builder.modules.config.types.SchemaHandlingMode;
import io.debezium.server.dist.builder.modules.config.types.SchemaHistoryInternalConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;


// Mapping done
public class MysqlSourceNode extends SqlBasedConnectorConfig implements ModuleNode {
    private final String ARTIFACT_ID = DEBEZIUM_CONNECTOR_PREFIX + "mysql";
    private final String connectorClass = "io.debezium.connector.mysql.MySqlConnector";
    private Integer databaseServerId;

    private BigintHandlingMode bigintUnsignedHandlingMode;

    private Boolean includeSchemaComments;

    private Boolean includeQuery;

    private FailureHandlingMode eventDeserializationFailureHandlingMode;

    private SchemaHandlingMode inconsistentSchemaHandlingMode;

    private Integer maxBatchSize;
    private Integer maxQueueSize;
    private Long maxQueueSizeInBytes;
    private Integer pollIntervalMs;
    private Integer connectTimeoutMs;
    private List<String> gtidSourceIncludes;
    private List<String> gtidSourceExcludes;
    private Boolean skipMessagesWithoutChange;

    private LockingMode snapshotLockingMode;

    private Boolean connectKeepAlive;

    private Boolean tableIgnoreBuiltin;

    private DatabaseSslMode databaseSslMode;

    private Integer binlogBufferSize;

    private Integer minRowCountToStreamResults;

    private String heartbeatActionQuery;

    private List<String> databaseInitialStatements;

    private Boolean enableTimeAdjuster;

    private Boolean incrementalSnapshotAllowSchemaChanges;

    private Boolean readOnly;

    private Boolean snapshotTablesOrderByRowCount;

    private HashMap<String, String> customMetricTags;

    // Schema history topic configuration

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
