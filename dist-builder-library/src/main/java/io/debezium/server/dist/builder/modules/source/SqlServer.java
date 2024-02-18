package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
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

import java.util.HashMap;
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
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    @Override
    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put(debeziumServerSourcePrefix + "database.instance", databaseInstance);
        builder.putList(debeziumServerSourcePrefix + "database.names", databaseNames);
        builder.putList(debeziumServerSourcePrefix + "schema.include.list", schemaIncludeList);
        builder.putList(debeziumServerSourcePrefix + "schema.exclude.list", schemaExcludeList);
        builder.putBoolean(debeziumServerSourcePrefix + "skip.messages.without.change", skipMessagesWithoutChange);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "snapshot.locking.mode", snapshotLockingMode);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "snapshot.isolation.mode", snapshotIsolationMode);
        builder.put(debeziumServerSourcePrefix + "poll.interval.ms", pollIntervalMs);
        builder.put(debeziumServerSourcePrefix + "query.fetch.size", queryFetchSize);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "source.struct.version", sourceStructVersion);
        builder.put(debeziumServerSourcePrefix + "retriable.restart.connector.wait.ms", retriableRestartConnectorWaitMs);
        builder.putBoolean(debeziumServerSourcePrefix + "incremental.snapshot.allow.schema.changes", incrementalSnapshotAllowSchemaChanges);
        builder.put(debeziumServerSourcePrefix + "max.iteration.transactions", maxIterationTransaction);
        builder.putBoolean(debeziumServerSourcePrefix + "incremental.snapshot.option.recompile", incrementalSnapshotAllowSchemaChanges);
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        propertiesBuilder.put(debeziumServerSourcePrefix + "connector.class", connectorClass);
        getCommonConfig(propertiesBuilder);
        propertiesBuilder.putAll(schemaHistoryInternalConfig);
        return propertiesBuilder.getProperties();
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getCommonConfig(yamlBuilder);
        yamlBuilder.putAll(schemaHistoryInternalConfig);
        return yamlBuilder.getYaml();
    }
}
