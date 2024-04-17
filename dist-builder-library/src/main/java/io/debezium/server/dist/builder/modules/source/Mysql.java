/*
 * MIT License
 *
 * Copyright (c) [2024] [Ondrej Babec <ond.babec@gmail.com>]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE
 * ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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

    public Mysql() {
    }


    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    public <C extends Config> void getMysqlCommonConfig(ConfigBuilder<C> builder) {
        builder.put(debeziumServerSourcePrefix + "database.server.id", databaseServerId);
        builder.put(debeziumServerSourcePrefix + "bigint.unsigned.handling.mode", bigintUnsignedHandlingMode);
        builder.putBoolean(debeziumServerSourcePrefix + "include.schema.comments", includeSchemaComments);
        builder.putBoolean(debeziumServerSourcePrefix + "include.query", includeQuery);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "event.deserialization.failure.handling.mode", eventDeserializationFailureHandlingMode);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "inconsistent.schema.handling.mode", inconsistentSchemaHandlingMode);
        builder.put(debeziumServerSourcePrefix + "poll.interval.ms", pollIntervalMs);
        builder.put(debeziumServerSourcePrefix + "connect.timeout.ms", connectTimeoutMs);
        builder.putList(debeziumServerSourcePrefix + "gtid.source.includes", gtidSourceIncludes);
        builder.putList(debeziumServerSourcePrefix + "gtid.source.excludes", gtidSourceExcludes);
        builder.putBoolean(debeziumServerSourcePrefix + "skip.messages.without.change", skipMessagesWithoutChange);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "snapshot.locking.mode", snapshotLockingMode);
        builder.putBoolean(debeziumServerSourcePrefix + "connect.keep.alive", connectKeepAlive);
        builder.putBoolean(debeziumServerSourcePrefix + "table.ignore.builtin", tableIgnoreBuiltin);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "database.ssl.mode", databaseSslMode);
        builder.put(debeziumServerSourcePrefix + "binlog.buffer.size", binlogBufferSize);
        builder.put(debeziumServerSourcePrefix + "min.row.count.to.stream.results", minRowCountToStreamResults);
        builder.put(debeziumServerSourcePrefix + "heartbeat.action.query", heartbeatActionQuery);
        builder.putList(debeziumServerSourcePrefix + "database.initial.statements", databaseInitialStatements);
        builder.putBoolean(debeziumServerSourcePrefix + "enable.time.adjuster", enableTimeAdjuster);
        builder.putBoolean(debeziumServerSourcePrefix + "incremental.snapshot.allow.schema.changes", incrementalSnapshotAllowSchemaChanges);
        builder.putBoolean(debeziumServerSourcePrefix + "read.only", readOnly);
        builder.putBoolean(debeziumServerSourcePrefix + "snapshot.tables.order.by.row.count", snapshotTablesOrderByRowCount);
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();

        YamlBuilder config = new YamlBuilder(super.toYaml());
        getMysqlCommonConfig(config);
        yamlBuilder.putAllMap(config.getYaml());
        return yamlBuilder.getYaml();
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        propertiesBuilder.put(debeziumServerSourcePrefix + "connector.class", connectorClass);
        getMysqlCommonConfig(propertiesBuilder);
        return propertiesBuilder.getProperties();
    }
}
