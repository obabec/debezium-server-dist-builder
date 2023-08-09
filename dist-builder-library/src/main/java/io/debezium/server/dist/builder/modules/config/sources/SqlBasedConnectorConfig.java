package io.debezium.server.dist.builder.modules.config.sources;

import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.sources.types.BinaryHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.DecimalHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.ProcessingFailureHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.TimePrecisionMode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Properties;


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


    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.hostname", databaseHostname);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.port", databasePort);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.user", databaseUser);
        propertiesBuilder.put(debeziumServerSourcePrefix + "database.password", databasePassword);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "table.include.list", tableIncludeList);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "table.exclude.list", tableExcludeList);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "column.exclude.list", columnExcludeList);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "column.include.list", columnIncludeList);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "time.precision.mode", timePrecisionMode);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "decimal.handling.mode", decimalHandlingMode);
        propertiesBuilder.put(debeziumServerSourcePrefix + "include.schema.changes", includeSchemaChanges);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "column.truncate.to.length.chars", columnTruncateToLengthChars);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "column.mask.with.length.chars", columnMaskWithLengthChars);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "column.propagate.source.type", columnPropagateSourceType);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "datatype.propagate.source.type", datatypePropagateSourceType);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "message.key.columns", messageKeyColumns);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "converters", converters);
        propertiesBuilder.putEnumWithLowerCase(debeziumServerSourcePrefix + "binary.handling.mode", binaryHandlingMode);
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.lock.timeout.ms", snapshotLockTimeoutMs);
        propertiesBuilder.putList(debeziumServerSourcePrefix + "snapshot.select.statement.overrides", snapshotSelectStatementOverrides);
        propertiesBuilder.putEnum(debeziumServerSourcePrefix + "event.processing.failure.handling.mode", eventProcessingFailureHandlingMode);
        return propertiesBuilder.getProperties();
    }

}
