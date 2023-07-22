package io.debezium.server.dist.builder.modules.config;

import io.debezium.server.dist.builder.modules.config.types.BinaryHandlingMode;
import io.debezium.server.dist.builder.modules.config.types.DecimalHandlingMode;
import io.debezium.server.dist.builder.modules.config.types.TimePrecisionMode;

import java.util.List;

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





    protected Boolean eventProcessingFailureHandlingMode;

}
