package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.sources.SqlBasedConnectorConfig;
import io.debezium.server.dist.builder.modules.config.sources.logmine.LogMiningConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.BinaryHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.IntervalHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.OracleConnectionAdapter;
import io.debezium.server.dist.builder.modules.config.sources.types.SchemaHistoryInternalConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.SnapshotLockingMode;
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
public class Oracle extends SqlBasedConnectorConfig implements SourceNode {
    private final String ARTIFACT_ID = DEBEZIUM_CONNECTOR_PREFIX + "oracle";

    private final String connectorClass = "io.debezium.connector.oracle.OracleConnector";
    // TODO: check if this is list of IP addresses
    protected List<String> racNodes;
    private String databaseDbname;
    private String databaseUrl;
    private String databasePbdName;
    private OracleConnectionAdapter databaseConnectionAdapter;
    private List<String> schemaIncludeList;
    private Boolean skipMessagesWithoutChange;
    private Boolean includeSchemaComments;
    private List<String> schemaExcludeList;
    private BinaryHandlingMode binaryHandlingMode;
    private IntervalHandlingMode intervalHandlingMode;
    private Integer pollIntervalMs;
    private Integer heartbeatIntervalMs;
    private String heartbeatActionQuery;
    private Integer queryFetchSize;
    private SnapshotLockingMode snapshotLockingMode;
    private LogMiningConfig logMiningConfig;
    private Boolean lobEnabled;
    private String unavailableValuePlaceholder;
    private SchemaHistoryInternalConfig schemaHistoryInternalConfig;

    public Oracle() {
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
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        propertiesBuilder.put(debeziumServerSourcePrefix + "connector.class", connectorClass);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.dbname", databaseDbname);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.url", databaseUrl);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.pdb.name", databasePbdName);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "database.connection.adapter", databaseConnectionAdapter);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "snapshot.select.statement.overrides", snapshotSelectStatementOverrides);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "schema.include.list", schemaIncludeList);
        propertiesBuilder.putBoolean(debeziumServerSourcePrefix + "skip.messages.without.change", skipMessagesWithoutChange);
        propertiesBuilder.putBoolean(debeziumServerSourcePrefix + "include.schema.comments", includeSchemaComments);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "schema.exclude.list", schemaExcludeList);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "binary.handling.mode", binaryHandlingMode);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "interval.handling.mode", intervalHandlingMode);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "event.processing.failure.handling.mode", eventProcessingFailureHandlingMode);
        propertiesBuilder.put(debeziumServerSourcePrefix + "poll.interval.ms", pollIntervalMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "heartbeat.interval.ms", heartbeatIntervalMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "heartbeat.action.query", heartbeatActionQuery);
        propertiesBuilder.put(debeziumServerSourcePrefix + "query.fetch.size", queryFetchSize);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "snapshot.locking.mode", snapshotLockingMode);
        propertiesBuilder.putAll(logMiningConfig);
        propertiesBuilder.putBoolean(debeziumServerSourcePrefix + "lob.enabled", lobEnabled);
        propertiesBuilder.put(debeziumServerSourcePrefix + "unavailable.value.placeholder", unavailableValuePlaceholder);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "rac.nodes", racNodes);
        propertiesBuilder.putAll(schemaHistoryInternalConfig);

        return propertiesBuilder.getProperties();
    }
}
