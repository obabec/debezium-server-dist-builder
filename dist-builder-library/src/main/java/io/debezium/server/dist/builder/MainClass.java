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
