package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.sources.SqlBasedConnectorConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.BigintHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.DatabaseSslMode;
import io.debezium.server.dist.builder.modules.config.sources.types.FailureHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.LockingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SchemaHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SchemaHistoryInternalConfig;
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
public class Mysql extends SqlBasedConnectorConfig implements SourceNode {
    private final String ARTIFACT_ID = DEBEZIUM_CONNECTOR_PREFIX + "mysql";
    private final String connectorClass = "io.debezium.connector.mysql.MySqlConnector";
    private Integer databaseServerId;

    private BigintHandlingMode bigintUnsignedHandlingMode;

    private Boolean includeSchemaComments;

    private Boolean includeQuery;

    private FailureHandlingMode eventDeserializationFailureHandlingMode;

    private SchemaHandlingMode inconsistentSchemaHandlingMode;

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

    //private HashMap<String, String> customMetricTags;

    // Schema history topic configuration

    private SchemaHistoryInternalConfig schemaHistoryInternalConfig;

    public Mysql() {
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
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.server.id", databaseServerId);
        propertiesBuilder.put(debeziumServerSourcePrefix + "bigint.unsigned.handling.mode", bigintUnsignedHandlingMode);
        propertiesBuilder.put(debeziumServerSourcePrefix + "include.schema.comments", includeSchemaComments.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "include.query", includeQuery.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "event.deserialization.failure.handling.mode", eventDeserializationFailureHandlingMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "inconsistent.schema.handling.mode", inconsistentSchemaHandlingMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "poll.interval.ms", pollIntervalMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "connect.timeout.ms", connectTimeoutMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "gtid.source.includes", String.join(",", gtidSourceIncludes));
        propertiesBuilder.put(debeziumServerSourcePrefix + "gtid.source.excludes", String.join(",", gtidSourceExcludes));
        propertiesBuilder.put(debeziumServerSourcePrefix + "skip.messages.without.change", skipMessagesWithoutChange.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.locking.mode", snapshotLockingMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "connect.keep.alive", connectKeepAlive.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "table.ignore.builtin", tableIgnoreBuiltin.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.ssl.mode", databaseSslMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "binlog.buffer.size", binlogBufferSize);
        propertiesBuilder.put(debeziumServerSourcePrefix + "min.row.count.to.stream.results", minRowCountToStreamResults);
        propertiesBuilder.put(debeziumServerSourcePrefix + "heartbeat.action.query", heartbeatActionQuery);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.initial.statements", String.join(",", databaseInitialStatements));
        propertiesBuilder.put(debeziumServerSourcePrefix + "enable.time.adjuster", enableTimeAdjuster.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "incremental.snapshot.allow.schema.changes", incrementalSnapshotAllowSchemaChanges.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "read.only", readOnly.toString());
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.tables.order.by.row.count", snapshotTablesOrderByRowCount.toString());
        if (schemaHistoryInternalConfig != null) {
            propertiesBuilder.putAll(schemaHistoryInternalConfig.toProperties());
        }
        return propertiesBuilder.getProperties();
    }
}
