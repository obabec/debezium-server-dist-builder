package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.types.BinaryHandlingMode;
import io.debezium.server.dist.builder.modules.types.IntervalHandlingMode;
import io.debezium.server.dist.builder.modules.types.OracleConnectionAdapter;
import io.debezium.server.dist.builder.modules.types.ProcessingFailureHandlingMode;
import io.debezium.server.dist.builder.modules.types.SchemaNameAdjustmentMode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;

public class OracleSourceNode implements ModuleNode {
    private final String ARTIFACT_ID = DEBEZIUM_CONNECTOR_PREFIX + "oracle";

    private final String connectorClass = "io.debezium.connector.oracle.OracleConnector";

    private String databaseDbname;

    private String databaseUrl;

    private String databasePbdName;

    private OracleConnectionAdapter databaseConnectionAdapter;

    private String snapshotSelectStatementOverrides;

    private List<String> schemaIncludeList;

    private Boolean includeSchemaComments;
    private List<String> schemaExcludeList;

    private BinaryHandlingMode binaryHandlingMode;

    private IntervalHandlingMode intervalHandlingMode;

    private ProcessingFailureHandlingMode eventProcessingFailureHandlingMode;

    private Integer pollIntervalMs;

    private Integer heartbeatIntervalMs;

    private String heartbeatActionQuery;

    private Integer queryFetchSize;

    private Boolean provideTransactionMetadata;

    // TODO: logmining config


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
