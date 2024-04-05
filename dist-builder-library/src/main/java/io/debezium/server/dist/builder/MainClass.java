package io.debezium.server.dist.builder;

import java.io.FileOutputStream;
import java.io.IOException;

import io.debezium.server.dist.builder.metadata.MetadataGenerator;
import io.debezium.server.dist.builder.utils.DeserializationUtils;

public class MainClass {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
/*        String json = "{\"version\":\"2.6.0.Alpha2\",\"sourceNode\":{\"Mysql\":{\"databaseServerId\":5467,\"bigintUnsignedHandlingMode\":\"\",\"includeSchemaComments\":\"\",\"includeQuery\":\"\",\"eventDeserializationFailureHandlingMode\":\"\",\"inconsistentSchemaHandlingMode\":\"\",\"pollIntervalMs\":\"\",\"connectTimeoutMs\":\"\",\"gtidSourceIncludes\":\"\",\"gtidSourceExcludes\":\"\",\"skipMessagesWithoutChange\":\"\",\"snapshotLockingMode\":\"\",\"connectKeepAlive\":\"\",\"tableIgnoreBuiltin\":\"\",\"databaseSslMode\":\"\",\"binlogBufferSize\":\"\",\"minRowCountToStreamResults\":\"\",\"heartbeatActionQuery\":\"\",\"databaseInitialStatements\":\"\",\"enableTimeAdjuster\":\"\",\"incrementalSnapshotAllowSchemaChanges\":\"\",\"readOnly\":\"\",\"snapshotTablesOrderByRowCount\":\"\",\"databaseHostname\":\"dell-r640-015.bkr.lab.eng.rdu2.dc.redhat.com\",\"databasePort\":3306,\"databaseUser\":\"debezium\",\"databasePassword\":\"debezium\",\"tableIncludeList\":\"\",\"tableExcludeList\":\"\",\"columnIncludeList\":\"\",\"columnExcludeList\":\"\",\"timePrecisionMode\":\"\",\"decimalHandlingMode\":\"\",\"includeSchemaChanges\":\"\",\"columnTruncateToLengthChars\":\"\",\"columnMaskWithLengthChars\":\"\",\"columnPropagateSourceType\":\"\",\"datatypePropagateSourceType\":\"\",\"messageKeyColumns\":\"\",\"converters\":\"\",\"binaryHandlingMode\":\"\",\"snapshotLockTimeoutMs\":\"\",\"snapshotSelectStatementOverrides\":\"\",\"eventProcessingFailureHandlingMode\":\"\",\"name\":\"mysql-con\",\"topicPrefix\":\"\",\"databaseIncludeList\":\"ssltest\",\"databaseExcludeList\":\"\",\"snapshotMode\":\"\",\"snapshotIncludeCollectionList\":\"\",\"tasksMax\":1,\"snapshotMaxThreads\":\"\",\"tombstonesOnDelete\":\"\",\"snapshotDelayMs\":\"\",\"snapshotFetchSize\":\"\",\"schemaNameAdjustmentMode\":\"\",\"fieldNameAdjustmentMode\":\"\",\"maxBatchSize\":\"\",\"maxQueueSize\":\"\",\"maxQueueSizeInBytes\":\"\",\"heartbeatIntervalMs\":\"\",\"skippedOperations\":\"\",\"provideTransactionMetadata\":\"\",\"signalDataCollection\":\"\",\"signalEnabledChannels\":\"\",\"notificationsEnabledChannels\":\"\",\"incrementalSnapshotChunkSize\":\"\",\"topicNamingStrategy\":\"\",\"topicDelimiter\":\"\",\"topicCacheSize\":\"\",\"topicHeartbeatPrefix\":\"\",\"topicTransaction\":\"\",\"notificationsSinkTopicName\":\"\"}},\"sinkNode\":{\"Redis\":{\"redisAddress\":\"dell-r640-020.bkr.lab.eng.rdu2.dc.redhat.com:6379\",\"redisUser\":\"debezium\",\"redisPassword\":\"debezium\",\"redisSslEnabled\":true,\"redisNullKey\":\"\",\"redisNullValue\":\"\",\"redisBatchSize\":\"\",\"redisRetryInitialDelayMs\":\"\",\"redisRetryMaxDelayMs\":\"\",\"redisConnectionTimeoutMs\":\"\",\"redisSocketTimeoutMs\":\"\",\"redisWaitEnabled\":\"\",\"redisWaitTimeoutMs\":\"\",\"redisWaitRetryEnabled\":\"\",\"redisWaitRetryDelayMs\":\"\",\"redisMessageFormat\":\"\",\"redisMemoryThresholdPercentage\":\"\",\"redisMemoryLimitMb\":\"\",\"ioDebeziumServerStreamNameMapper\":\"\"}},\"internalSchemaHistory\":{\"schemaHistoryClass\":\"\",\"connectorName\":\"\",\"connectorId\":\"\",\"skipUnparseableDdl\":\"\",\"storeOnlyCapturedTablesDdl\":\"\",\"storeOnlyCapturedDatabasesDdl\":\"\",\"ddlFilter\":\"\",\"preferDdl\":\"\",\"storageConfig\":{\"RocketMQStorageConfig\":{\"topic\":\"\",\"nameSrvAddr\":\"\",\"aclEnabled\":\"\",\"accessKey\":\"\",\"secretKey\":\"\",\"recoveryAttempts\":\"\",\"recoveryPollIntervalMs\":\"\",\"storeRecordTimeoutMs\":\"\"}}},\"offsetStorage\":{\"offsetStorage\":\"\",\"storageConfig\":{\"FileStorage\":{\"fileFilename\":\"\"}}},\"dependencyList\":[]}";
        CustomDebeziumServer sr = DeserializationUtils.getDefaultMapper().readValue(json, CustomDebeziumServer.class);

        new DebeziumServerDistributionBuilder()
                .withDebeziumServer(sr)
                .withDefaultRepository("/tmp/server")
                .build()
                .generateConfigurationProperties()
                .generateOperatorCR();
        int s = 5;*/

        MetadataGenerator metadataGenerator = new MetadataGenerator();
        metadataGenerator.generateSourceNodeMetadata(new FileOutputStream("metadata-source-js.json"));
        metadataGenerator.generateMetadata(new FileOutputStream("main-metadata.json"));
    }
}
