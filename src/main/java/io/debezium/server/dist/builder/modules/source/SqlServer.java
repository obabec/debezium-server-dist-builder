package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.sources.SqlBasedConnectorConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.SchemaHistoryInternalConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.SnapshotIsolationMode;
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

    //private HashMap<String, String> customMetricTags;
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

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        propertiesBuilder.put(debeziumServerSourcePrefix + "connector.class", connectorClass);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.instance", databaseInstance);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.names", String.join(",", databaseNames));
        propertiesBuilder.put(debeziumServerSourcePrefix + "schema.include.list", String.join(",", schemaIncludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "schema.exclude.list", String.join(",", schemaExcludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "skip.messages.without.change", skipMessagesWithoutChange.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.locking.mode", snapshotLockingMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.isolation.mode", snapshotIsolationMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "poll.interval.ms", pollIntervalMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "query.fetch.size", queryFetchSize);
        propertiesBuilder.put(debeziumServerSourcePrefix + "source.struct.version", sourceStructVersion.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "retriable.restart.connector.wait.ms", retriableRestartConnectorWaitMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "incremental.snapshot.allow.schema.changes", incrementalSnapshotAllowSchemaChanges.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "max.iteration.transactions", maxIterationTransaction);
        propertiesBuilder.put(debeziumServerSourcePrefix + "incremental.snapshot.option.recompile", incrementalSnapshotAllowSchemaChanges.toString());
        if (schemaHistoryInternalConfig != null) {
            propertiesBuilder.putAll(schemaHistoryInternalConfig.toProperties());
        }
        return propertiesBuilder.getProperties();
    }
}
