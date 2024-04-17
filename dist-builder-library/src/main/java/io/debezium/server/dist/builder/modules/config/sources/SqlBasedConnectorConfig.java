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

package io.debezium.server.dist.builder.modules.config.sources;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import io.debezium.server.dist.builder.modules.config.sources.types.BinaryHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.DecimalHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.ProcessingFailureHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.TimePrecisionMode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SqlBasedConnectorConfig extends ConnectorConfig {
    protected String databaseHostname;
    protected Integer databasePort;
    protected String databaseUser;
    protected String databasePassword;

    protected List<String> tableIncludeList;
    protected List<String> tableExcludeList;
    protected List<String> columnIncludeList;
    protected List<String> columnExcludeList;


/*
    column.mask.hash.hashAlgorithm.with.salt.salt; column.mask.hash.v2.hashAlgorithm.with.salt.salt
*/


    // TODO: I will have to more investigate possible values for this attribute
    protected TimePrecisionMode timePrecisionMode;

    protected DecimalHandlingMode decimalHandlingMode;
    protected Boolean includeSchemaChanges;

    protected List<String> columnTruncateToLengthChars;
    protected List<String> columnMaskWithLengthChars;
    protected List<String> columnPropagateSourceType;
    protected List<String> datatypePropagateSourceType;
    protected List<String> messageKeyColumns;

    protected List<String> converters;

    protected BinaryHandlingMode binaryHandlingMode;

    protected Integer snapshotLockTimeoutMs;

    protected List<String> snapshotSelectStatementOverrides;
    protected ProcessingFailureHandlingMode eventProcessingFailureHandlingMode;


    public <C extends Config> void getSqlCommonConfig(ConfigBuilder<C> builder) {
        builder.put(debeziumServerSourcePrefix + "database.hostname", databaseHostname);
        builder.put(debeziumServerSourcePrefix + "database.port", databasePort);
        builder.put(debeziumServerSourcePrefix + "database.user", databaseUser);
        builder.put(debeziumServerSourcePrefix + "database.password", databasePassword);
        builder.putList(debeziumServerSourcePrefix + "table.include.list", tableIncludeList);
        builder.putList(debeziumServerSourcePrefix + "table.exclude.list", tableExcludeList);
        builder.putList(debeziumServerSourcePrefix + "column.exclude.list", columnExcludeList);
        builder.putList(debeziumServerSourcePrefix + "column.include.list", columnIncludeList);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "time.precision.mode", timePrecisionMode);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "decimal.handling.mode", decimalHandlingMode);
        builder.put(debeziumServerSourcePrefix + "include.schema.changes", includeSchemaChanges);
        builder.putList(debeziumServerSourcePrefix + "column.truncate.to.length.chars", columnTruncateToLengthChars);
        builder.putList(debeziumServerSourcePrefix + "column.mask.with.length.chars", columnMaskWithLengthChars);
        builder.putList(debeziumServerSourcePrefix + "column.propagate.source.type", columnPropagateSourceType);
        builder.putList(debeziumServerSourcePrefix + "datatype.propagate.source.type", datatypePropagateSourceType);
        builder.putList(debeziumServerSourcePrefix + "message.key.columns", messageKeyColumns);
        builder.putList(debeziumServerSourcePrefix + "converters", converters);
        builder.putEnumWithLowerCase(debeziumServerSourcePrefix + "binary.handling.mode", binaryHandlingMode);
        builder.put(debeziumServerSourcePrefix + "snapshot.lock.timeout.ms", snapshotLockTimeoutMs);
        builder.putList(debeziumServerSourcePrefix + "snapshot.select.statement.overrides", snapshotSelectStatementOverrides);
        builder.putEnum(debeziumServerSourcePrefix + "event.processing.failure.handling.mode", eventProcessingFailureHandlingMode);
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder(super.toYaml());
        getSqlCommonConfig(yamlBuilder);
        return yamlBuilder.getYaml();
    }


    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        getSqlCommonConfig(propertiesBuilder);
        return propertiesBuilder.getProperties();
    }

}
