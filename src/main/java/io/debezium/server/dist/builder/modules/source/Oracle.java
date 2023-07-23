package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.sources.SqlBasedConnectorConfig;
import io.debezium.server.dist.builder.modules.config.sources.logmine.LogMiningConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.BinaryHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.IntervalHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.OracleConnectionAdapter;
import io.debezium.server.dist.builder.modules.config.sources.types.ProcessingFailureHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SchemaHistoryInternalConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.SnapshotLockingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SnapshotMode;
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

    private SnapshotLockingMode snapshotLockingMode;

    private LogMiningConfig logMiningConfig;
    private Boolean lobEnabled;
    private String unavailableValuePlaceholder;


    // TODO: check if this is list of IP addresses
    protected List<String> racNodes;
    private SchemaHistoryInternalConfig schemaHistoryInternalConfig;

    public Oracle() {
    }



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
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.dbname", databaseDbname);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.url", databaseUrl);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.pdb.name", databasePbdName);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.connection.adapter", databaseConnectionAdapter.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.select.statement.overrides", snapshotSelectStatementOverrides);
        propertiesBuilder.put(debeziumServerSourcePrefix + "schema.include.list", String.join(",", schemaIncludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "skip.messages.without.change", skipMessagesWithoutChange.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "include.schema.comments", includeSchemaComments.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "schema.exclude.list", String.join(",", schemaExcludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "binary.handling.mode", binaryHandlingMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "interval.handling.mode", intervalHandlingMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "event.processing.failure.handling.mode", eventProcessingFailureHandlingMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "poll.interval.ms", pollIntervalMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "heartbeat.interval.ms", heartbeatIntervalMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "heartbeat.action.query", heartbeatActionQuery);
        propertiesBuilder.put(debeziumServerSourcePrefix + "query.fetch.size", queryFetchSize);
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.locking.mode", snapshotLockingMode.toString().toLowerCase());
        if (logMiningConfig != null) {
            propertiesBuilder.putAll(logMiningConfig.toProperties());
        }
        propertiesBuilder.put(debeziumServerSourcePrefix + "lob.enabled", lobEnabled.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "unavailable.value.placeholder", unavailableValuePlaceholder);
        propertiesBuilder.put(debeziumServerSourcePrefix + "rac.nodes", String.join(",", racNodes));
        if (schemaHistoryInternalConfig != null) {
            propertiesBuilder.putAll(schemaHistoryInternalConfig.toProperties());
        }
        return propertiesBuilder.getProperties();
    }
}
