package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.config.sources.SqlBasedConnectorConfig;
import io.debezium.server.dist.builder.modules.config.sources.logmine.LogMiningConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.BinaryHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.IntervalHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.OracleConnectionAdapter;
import io.debezium.server.dist.builder.modules.config.sources.types.ProcessingFailureHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SchemaHistoryInternalConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;

// Mapping done
public class Oracle extends SqlBasedConnectorConfig implements ModuleNode {
    private final String ARTIFACT_ID = DEBEZIUM_CONNECTOR_PREFIX + "oracle";

    private final String connectorClass = "io.debezium.connector.oracle.OracleConnector";

    private String databaseDbname;

    private String databaseUrl;

    private String databasePbdName;

    private OracleConnectionAdapter databaseConnectionAdapter;

    private String snapshotSelectStatementOverrides;

    private List<String> schemaIncludeList;

    private Boolean skipMessagesWithoutChange;


    private Boolean includeSchemaComments;
    private List<String> schemaExcludeList;

    private BinaryHandlingMode binaryHandlingMode;

    private IntervalHandlingMode intervalHandlingMode;

    private ProcessingFailureHandlingMode eventProcessingFailureHandlingMode;

    private Integer pollIntervalMs;

    private Integer heartbeatIntervalMs;

    private String heartbeatActionQuery;

    private Integer queryFetchSize;

    private String snapshotLockingMode;

    private LogMiningConfig logMiningConfig;
    private Boolean lobEnabled;
    private String unavailableValuePlaceholder;


    // TODO: check if this is list of IP addresses
    protected List<String> racNodes;
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
