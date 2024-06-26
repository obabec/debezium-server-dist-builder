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
import io.debezium.server.dist.builder.modules.config.sources.types.SchemaHistoryInternalConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.SnapshotIsolationMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SnapshotLockingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.SourceStructVersion;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


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

    public <C extends Config> void getSqlServerCommonConfig(ConfigBuilder<C> builder) {
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
        getSqlServerCommonConfig(propertiesBuilder);
        return propertiesBuilder.getProperties();
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getSqlServerCommonConfig(yamlBuilder);
        return yamlBuilder.getYaml();
    }
}
