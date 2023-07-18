package io.debezium.server.dist.builder.modules;

import io.debezium.server.dist.builder.modules.types.BinaryHandlingMode;
import io.debezium.server.dist.builder.modules.types.DecimalHandlingMode;
import io.debezium.server.dist.builder.modules.types.NameAdjustmentMode;
import io.debezium.server.dist.builder.modules.types.SchemaNameAdjustmentMode;
import io.debezium.server.dist.builder.modules.types.SnapshotMode;
import io.debezium.server.dist.builder.modules.types.TimePrecisionMode;

import java.util.List;

public abstract class SourceNodeConfiguration implements ModuleNode {

    protected String name;
    protected Integer tasksMax;

    protected String databaseHostname;
    protected Integer databasePort;
    protected String databaseUser;
    protected String databasePassword;
    protected String topicPrefix;

    protected List<String> databaseIncludeList;
    protected List<String> databaseExcludeList;
    protected List<String> tableIncludeList;
    protected List<String> tableExcludeList;
    protected List<String> columnIncludeList;
    protected List<String> columnExcludeList;

    protected Boolean skipMessagesWithoutChange;

/*
    column.mask.hash.hashAlgorithm.with.salt.salt; column.mask.hash.v2.hashAlgorithm.with.salt.salt
*/


    // TODO: I will have to more investigate possible values for this attribute
    protected TimePrecisionMode timePrecisionMode;

    protected DecimalHandlingMode decimalHandlingMode;
    protected Boolean includeSchemaChanges;

    protected Boolean tombstoneOnDelete;

    protected List<String> columnTruncateToLengthChars;
    protected List<String> columnMaskWithLengthChars;
    protected List<String> columnPropagateSourceType;
    protected List<String> datatypePropagateSourceType;
    protected List<String> messageKeyColumns;

    protected NameAdjustmentMode schemaNameAdjustmentMode;
    protected NameAdjustmentMode fieldNameAdjustmentMode;

    protected List<String> converters;

    protected SnapshotMode snapshotMode;

    protected String snapshotLockingMode;

    protected List<String> snapshotIncludeCollectionList;

    protected BinaryHandlingMode binaryHandlingMode;

    protected Integer matBatchSize;

    protected Integer maxQueueSize;

    protected Long maxQueueSizeInBytes;

    protected Integer snapshotDelayMs;

    protected Integer snapshotFetchSize;
}
