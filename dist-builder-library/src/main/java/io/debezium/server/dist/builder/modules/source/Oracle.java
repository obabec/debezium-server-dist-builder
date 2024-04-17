package io.debezium.server.dist.builder.modules.source;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
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

    public <C extends Config> void getOracleCommonConfig(ConfigBuilder<C> builder) {
        builder.put(debeziumServerSourcePrefix + "database.dbname", databaseDbname);
        builder.put(debeziumServerSourcePrefix + "database.url", databaseUrl);
        builder.put(debeziumServerSourcePrefix + "database.pdb.name", databasePbdName);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "database.connection.adapter", databaseConnectionAdapter);
        builder.putList(debeziumServerSourcePrefix + "snapshot.select.statement.overrides", snapshotSelectStatementOverrides);
        builder.putList(debeziumServerSourcePrefix + "schema.include.list", schemaIncludeList);
        builder.putBoolean(debeziumServerSourcePrefix + "skip.messages.without.change", skipMessagesWithoutChange);
        builder.putBoolean(debeziumServerSourcePrefix + "include.schema.comments", includeSchemaComments);
        builder.putList(debeziumServerSourcePrefix + "schema.exclude.list", schemaExcludeList);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "binary.handling.mode", binaryHandlingMode);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "interval.handling.mode", intervalHandlingMode);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "event.processing.failure.handling.mode", eventProcessingFailureHandlingMode);
        builder.put(debeziumServerSourcePrefix + "poll.interval.ms", pollIntervalMs);
        builder.put(debeziumServerSourcePrefix + "heartbeat.interval.ms", heartbeatIntervalMs);
        builder.put(debeziumServerSourcePrefix + "heartbeat.action.query", heartbeatActionQuery);
        builder.put(debeziumServerSourcePrefix + "query.fetch.size", queryFetchSize);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "snapshot.locking.mode", snapshotLockingMode);

        builder.putBoolean(debeziumServerSourcePrefix + "lob.enabled", lobEnabled);
        builder.put(debeziumServerSourcePrefix + "unavailable.value.placeholder", unavailableValuePlaceholder);
        builder.putList(debeziumServerSourcePrefix + "rac.nodes", racNodes);
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getOracleCommonConfig(yamlBuilder);
        yamlBuilder.putAll(logMiningConfig);
        return yamlBuilder.getYaml();
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        getOracleCommonConfig(propertiesBuilder);
        propertiesBuilder.put(debeziumServerSourcePrefix + "connector.class", connectorClass);
        propertiesBuilder.putAll(logMiningConfig);

        return propertiesBuilder.getProperties();
    }
}
