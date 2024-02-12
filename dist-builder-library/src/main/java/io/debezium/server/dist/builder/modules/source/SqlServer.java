package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.sources.SqlBasedConnectorConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.SchemaHistoryInternalConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.SnapshotIsolationMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SnapshotLockingMode;
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
public class SqlServer extends SqlBasedConnectorConfig implements SourceNode {
    private final String ARTIFACT_ID = DEBEZIUM_CONNECTOR_PREFIX + "sql";
    private final String connectorClass = "io.debezium.connector.sqlserver.SqlServerConnector";
    private String databaseInstance;

    private List<String> databaseNames;

    private List<String> schemaIncludeList;
    private List<String> schemaExcludeList;

    private Boolean skipMessagesWithoutChange;

    private SnapshotLockingMode snapshotLockingMode;

    private SnapshotIsolationMode snapshotIsolationMode;

    private Integer pollIntervalMs;

    private Integer queryFetchSize;

    private SourceStructVersion sourceStructVersion;

    private Integer retriableRestartConnectorWaitMs;

    private Boolean incrementalSnapshotAllowSchemaChanges;

    private Integer maxIterationTransaction;

    private Boolean incrementalSnapshotOptionRecompile;

    //private HashMap<String, String> customMetricTags;
    private SchemaHistoryInternalConfig schemaHistoryInternalConfig;

    public SqlServer() {
    }

    @Override
    public Node buildNode(Document document) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID);
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        propertiesBuilder.put(debeziumServerSourcePrefix + "connector.class", connectorClass);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.instance", databaseInstance);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "database.names", databaseNames);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "schema.include.list", schemaIncludeList);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "schema.exclude.list", schemaExcludeList);
        propertiesBuilder.putBoolean(debeziumServerSourcePrefix + "skip.messages.without.change", skipMessagesWithoutChange);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "snapshot.locking.mode", snapshotLockingMode);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "snapshot.isolation.mode", snapshotIsolationMode);
        propertiesBuilder.put(debeziumServerSourcePrefix + "poll.interval.ms", pollIntervalMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "query.fetch.size", queryFetchSize);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "source.struct.version", sourceStructVersion);
        propertiesBuilder.put(debeziumServerSourcePrefix + "retriable.restart.connector.wait.ms", retriableRestartConnectorWaitMs);
        propertiesBuilder.putBoolean(debeziumServerSourcePrefix + "incremental.snapshot.allow.schema.changes", incrementalSnapshotAllowSchemaChanges);
        propertiesBuilder.put(debeziumServerSourcePrefix + "max.iteration.transactions", maxIterationTransaction);
        propertiesBuilder.putBoolean(debeziumServerSourcePrefix + "incremental.snapshot.option.recompile", incrementalSnapshotAllowSchemaChanges);
        propertiesBuilder.putAll(schemaHistoryInternalConfig);
        return propertiesBuilder.getProperties();
    }
}
