package io.debezium.server.dist.builder.modules.config.sources;

import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.sources.types.BinaryHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.DecimalHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.ProcessingFailureHandlingMode;
import io.debezium.server.dist.builder.modules.config.sources.types.TimePrecisionMode;
import io.sundr.builder.annotations.Buildable;
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
        propertiesBuilder.put(debeziumServerSourcePrefix + "table.include.list", String.join(",", tableIncludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "table.exclude.list", String.join(",", tableExcludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "column.exclude.list", String.join(",", columnExcludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "column.include.list", String.join(",", columnIncludeList));
        propertiesBuilder.put(debeziumServerSourcePrefix + "time.precision.mode", timePrecisionMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "decimal.handling.mode", decimalHandlingMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "include.schema.changes", includeSchemaChanges);
        propertiesBuilder.put(debeziumServerSourcePrefix + "column.truncate.to.length.chars", String.join(",", columnTruncateToLengthChars));
        propertiesBuilder.put(debeziumServerSourcePrefix + "column.mask.with.length.chars", String.join(",", columnMaskWithLengthChars));
        propertiesBuilder.put(debeziumServerSourcePrefix + "column.propagate.source.type", String.join(",", columnPropagateSourceType));
        propertiesBuilder.put(debeziumServerSourcePrefix + "datatype.propagate.source.type", String.join(",", datatypePropagateSourceType));
        propertiesBuilder.put(debeziumServerSourcePrefix + "message.key.columns", String.join(",", messageKeyColumns));
        propertiesBuilder.put(debeziumServerSourcePrefix + "converters", String.join(",", converters));
        propertiesBuilder.put(debeziumServerSourcePrefix + "binary.handling.mode", binaryHandlingMode.toString().toLowerCase());
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.lock.timeout.ms", snapshotLockTimeoutMs);
        propertiesBuilder.put(debeziumServerSourcePrefix + "snapshot.select.statement.overrides", String.join(",", snapshotSelectStatementOverrides));
        propertiesBuilder.put(debeziumServerSourcePrefix + "event.processing.failure.handling.mode", eventProcessingFailureHandlingMode.toString());
        return propertiesBuilder.getProperties();
    }

}
