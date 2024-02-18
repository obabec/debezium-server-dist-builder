import io.debezium.operator.api.model.ConfigProperties;
import io.debezium.operator.api.model.DebeziumServerBuilder;
import io.debezium.server.dist.builder.CustomDebeziumServer;
import io.debezium.server.dist.builder.CustomDebeziumServerBuilder;
import io.debezium.server.dist.builder.DebeziumServerDistributionBuilder;
import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.config.sinks.types.RedisMessageFormat;

import java.io.IOException;
import java.util.List;

public class MainClass {
        public static void main(String[] args) throws ClassNotFoundException, IOException {
                ConfigProperties quarkusConfig = new ConfigProperties();
                quarkusConfig.setProps("kubernetes-config.enabled", "true");
                quarkusConfig.setProps("kubernetes-config.secrets", "postgresql-credentials");
                DebeziumServerBuilder operatorCRBuilder = new DebeziumServerBuilder()
                        .withNewSpec()
                        .withImage("custom_imate")
                        .withVersion("2.6.6")
                        .withNewQuarkus()
                                .withConfig(quarkusConfig)
                        .endQuarkus()
                        .endSpec();
                System.out.println("haha");
                int x = 5;

                CustomDebeziumServer dbp = new CustomDebeziumServerBuilder()
                        .withNewOracleSourceNode()
                                .withDatabaseDbname("test-dataabasasdasdasdase")
                                .withDatabaseUrl("testurl")
                                .withDatabasePassword("pass")
                                .withDatabaseUser("test-user")
                        .endOracleSourceNode()
                        .withNewRedisSinkNode()
                                .withRedisAddress("redis-address")
                                .withRedisBatchSize(3584)
                                .withRedisPassword("redis-pass")
                                .withRedisSslEnabled(true)
                                .withRedisMessageFormat(RedisMessageFormat.EXTENDED)
                        .endRedisSinkNode()
                        .withNewOffsetStorage()
                                .withOffsetStorage("io.debezium.storage.redis.offset.RedisOffsetBackingStore")
                                .withNewFileStorageConfig()
                                        .withFileFilename("testFile.txt")
                                .endFileStorageConfig()
                        .endOffsetStorage()
                        .withNewInternalSchemaHistory()
                                .withNewFileStorageConfig()
                                        .withFileFilename("internal.txt")
                                .endFileStorageConfig()
                                .withSchemaHistoryClass("io.debezium.storage.file.history.FileSchemaHistory")
                        .endInternalSchemaHistory()
                        .withDependencyList(List.of(new Dependency("io.skodjob", "test-frame", "0.0.1")))
                        .withOperatorCR(operatorCRBuilder.build())
                        .build();
/*                String json = "{\"sourceNode\":{\"SqlServer\":{\"databaseInstance\":\"asdasd\",\"databaseNames\":\"asdasd\",\"schemaIncludeList\":\"\",\"schemaExcludeList\":\"asd\",\"skipMessagesWithoutChange\":true,\"snapshotLockingMode\":\"NONE\",\"snapshotIsolationMode\":\"\",\"pollIntervalMs\":\"\",\"queryFetchSize\":\"\",\"sourceStructVersion\":\"\",\"retriableRestartConnectorWaitMs\":\"\",\"incrementalSnapshotAllowSchemaChanges\":\"\",\"maxIterationTransaction\":\"\",\"incrementalSnapshotOptionRecompile\":\"\",\"databaseHostname\":\"\",\"databasePort\":\"\",\"databaseUser\":\"\",\"databasePassword\":\"\",\"tableIncludeList\":\"\",\"tableExcludeList\":\"\",\"columnIncludeList\":\"\",\"columnExcludeList\":\"\",\"timePrecisionMode\":\"\",\"decimalHandlingMode\":\"\",\"includeSchemaChanges\":\"\",\"columnTruncateToLengthChars\":\"\",\"columnMaskWithLengthChars\":\"\",\"columnPropagateSourceType\":\"\",\"datatypePropagateSourceType\":\"\",\"messageKeyColumns\":\"\",\"converters\":\"\",\"binaryHandlingMode\":\"\",\"snapshotLockTimeoutMs\":\"\",\"snapshotSelectStatementOverrides\":\"\",\"eventProcessingFailureHandlingMode\":\"\",\"name\":\"\",\"topicPrefix\":\"\",\"databaseIncludeList\":\"\",\"databaseExcludeList\":\"\",\"snapshotMode\":\"\",\"snapshotIncludeCollectionList\":\"\",\"tasksMax\":\"\",\"snapshotMaxThreads\":\"\",\"tombstonesOnDelete\":\"\",\"snapshotDelayMs\":\"\",\"snapshotFetchSize\":\"\",\"schemaNameAdjustmentMode\":\"\",\"fieldNameAdjustmentMode\":\"\",\"maxBatchSize\":\"\",\"maxQueueSize\":\"\",\"maxQueueSizeInBytes\":\"\",\"heartbeatIntervalMs\":\"\",\"skippedOperations\":\"\",\"provideTransactionMetadata\":\"\",\"signalDataCollection\":\"\",\"signalEnabledChannels\":\"\",\"notificationsEnabledChannels\":\"\",\"incrementalSnapshotChunkSize\":\"\",\"topicNamingStrategy\":\"\",\"topicDelimiter\":\"\",\"topicCacheSize\":\"\",\"topicHeartbeatPrefix\":\"\",\"topicTransaction\":\"\",\"notificationsSinkTopicName\":\"\"}},\"sinkNode\":{\"NatsSteaming\":{\"natsStreamingUrl\":\"asdasd\",\"natsStreamingClusterId\":\"asda\",\"natsStreamingClientId\":\"sadasd\",\"ioNatsStreamingStreamingConnection\":\"\",\"ioDebeziumServerStreamNameMapper\":\"\"}},\"internalSchemaHistory\":{\"schemaHistoryClass\":\"asdasd\",\"storageConfig\":{\"RocketMQStorageConfig\":{\"topic\":\"asdasd\",\"nameSrvAddr\":\"asdasd\",\"aclEnabled\":true,\"accessKey\":\"asdasd\",\"secretKey\":\"\",\"recoveryAttempts\":\"\",\"recoveryPollIntervalMs\":\"\",\"storeRecordTimeoutMs\":\"\"}}},\"offsetStorage\":{\"offsetStorage\":\"asdasdasd\",\"storageConfig\":{\"RedisStorageConfig\":{\"address\":\"asdasd\",\"user\":\"asdasd\",\"password\":\"asdasd\",\"sslEnabled\":true,\"key\":\"\",\"waitEnabled\":\"\",\"waitTimeoutMs\":\"\",\"waitRetryEnabled\":\"\",\"waitRetryDelayMs\":\"\"}}},\"dependencyList\":[{\"groupId\":\"asdasd\",\"artifactId\":\"asdasd\",\"version\":\"213\",\"comment\":\"asdasdsddddsdasdasdasdasd\"},{\"groupId\":\"asdasd\",\"artifactId\":\"gfsigubfh\",\"version\":\"1684153\",\"comment\":\"mkljnou8hgwidjfnklopfhugbkjn\"}]}";
                DebeziumServer sr = DeserializationUtils.getDefaultMapper().readValue(json, DebeziumServer.class);*/

                //FileOutputStream outputStream = new FileOutputStream("/Users/obabec/Development/masters/debezium-server-dist-builder/test-result/res.zip");

                new DebeziumServerDistributionBuilder()
                        .withDebeziumServer(dbp)
                        .withVersion("2.3.2.Final")
                        .withDefaultRepository("/tmp/server")
                        .build()
                        .generateConfigurationProperties()
                        .generateOperatorCR();
                int s = 5;
                        //.mavenBuild("/usr/local/Cellar/maven/3.9.4/libexec");
                //"/Users/obabec/Development/masters/debezium-server-dist-builder/test-result/res.zip"
/*                MetadataGenerator metadataGenerator = new MetadataGenerator();
                metadataGenerator.generateMetadata(new FileOutputStream("metadata-js.json"));*/

                /*String json = "{\"sourceNode\":{\"Postgres\":{\"pluginName\":\"DECODERBUFS\",\"slotName\":\"asdfvdbg\",\"slotDropOnStop\":true,\"publicationName\":\"\",\"databaseDbname\":\"\",\"schemaIncludeList\":\"\",\"schemaExcludeList\":\"\",\"hstoreHandlingMode\":\"\",\"databaseSSLMode\":\"\",\"skipMessagesWithoutChange\":\"\",\"databaseSSLCert\":\"\",\"databaseSSLKey\":\"\",\"databaseSSLPassword\":\"\",\"databaseSSLRootCert\":\"\",\"databaseTCPKeepAlive\":\"\",\"publicationAutocreateMode\":\"\",\"replicaIdentityAutosetValues\":\"\",\"moneyFractionDigits\":\"\",\"messagePrefixIncludeList\":\"\",\"messagePrefixExcludeList\":\"\",\"snapshotLockingMode\":\"\",\"snapshotCustomClass\":\"\",\"pollIntervalMs\":\"\",\"includeUnknownDatatypes\":\"\",\"databaseInitialStatements\":\"\",\"statusUpdateIntervalMs\":\"\",\"heartbeatActionQuery\":\"\",\"schemaRefreshMode\":\"\",\"slotStreamParams\":\"\",\"slotMaxRetries\":\"\",\"slotRetryDelayMs\":\"\",\"unavailableValuePlaceholder\":\"\",\"provideTransactionMetadata\":\"\",\"flushLsnSource\":\"\",\"retriableRestartConnectorWaitMs\":\"\",\"xminFetchIntervalMs\":\"\",\"databaseHostname\":\"\",\"databasePort\":\"\",\"databaseUser\":\"\",\"databasePassword\":\"\",\"tableIncludeList\":\"\",\"tableExcludeList\":\"\",\"columnIncludeList\":\"\",\"columnExcludeList\":\"\",\"timePrecisionMode\":\"\",\"decimalHandlingMode\":\"\",\"includeSchemaChanges\":\"\",\"columnTruncateToLengthChars\":\"\",\"columnMaskWithLengthChars\":\"\",\"columnPropagateSourceType\":\"\",\"datatypePropagateSourceType\":\"\",\"messageKeyColumns\":\"\",\"converters\":\"\",\"binaryHandlingMode\":\"\",\"snapshotLockTimeoutMs\":\"\",\"snapshotSelectStatementOverrides\":\"\",\"eventProcessingFailureHandlingMode\":\"\",\"name\":\"\",\"topicPrefix\":\"\",\"databaseIncludeList\":\"\",\"databaseExcludeList\":\"\",\"snapshotMode\":\"\",\"snapshotIncludeCollectionList\":\"\",\"tasksMax\":\"\",\"snapshotMaxThreads\":\"\",\"tombstonesOnDelete\":\"\",\"snapshotDelayMs\":\"\",\"snapshotFetchSize\":\"\",\"schemaNameAdjustmentMode\":\"\",\"fieldNameAdjustmentMode\":\"\",\"maxBatchSize\":\"\",\"maxQueueSize\":\"\",\"maxQueueSizeInBytes\":\"\",\"heartbeatIntervalMs\":\"\",\"skippedOperations\":\"\",\"signalDataCollection\":\"\",\"signalEnabledChannels\":\"\",\"notificationsEnabledChannels\":\"\",\"incrementalSnapshotChunkSize\":\"\",\"topicNamingStrategy\":\"\",\"topicDelimiter\":\"\",\"topicCacheSize\":\"\",\"topicHeartbeatPrefix\":\"\",\"topicTransaction\":\"\",\"notificationsSinkTopicName\":\"\"}},\"sinkNode\":{\"Pravega\":{\"pravegaControllerUri\":\"sdafgbf\",\"pravegaScope\":\"dasfg\",\"pravegaTransaction\":true,\"ioDebeziumServerStreamNameMapper\":\"\"}},\"internalSchemaHistory\":{\"schemaHistoryClass\":\"sadfgdg\",\"connectorName\":\"adsfg\",\"connectorId\":\"1324\",\"skipUnparseableDdl\":true,\"storeOnlyCapturedTablesDdl\":\"\",\"storeOnlyCapturedDatabasesDdl\":\"\",\"ddlFilter\":\"\",\"preferDdl\":\"\",\"storageConfig\":{\"S3StorageConfig\":{\"accessKeyId\":\"aedfsg\",\"secretAccessKey\":\"afedsg\",\"regionName\":\"adfsgb\",\"bucketName\":\"\",\"objectName\":\"\",\"endpoint\":\"\"}}},\"offsetStorage\":{\"offsetStorage\":\"asdfdgfd\",\"storageConfig\":{\"RedisStorageConfig\":{\"address\":\"adsfg\",\"user\":\"asdfgf\",\"password\":\"asfdgf\",\"sslEnabled\":true,\"key\":\"\",\"waitEnabled\":\"\",\"waitTimeoutMs\":\"\",\"waitRetryEnabled\":\"\",\"waitRetryDelayMs\":\"\"}}},\"dependencyList\":[{\"groupId\":\"qwedrfgtdhrfn\",\"artifactId\":\"wdeaqfsedwgsrehdtgn\",\"version\":\"12343\",\"comment\":\"wrqfaegdwrsfehdtnfgfwfeagrsfedtg\"}]}";
                DebeziumServer sr = DeserializationUtils.getDefaultMapper().readValue(json, DebeziumServer.class);
                int s = 5;*/
        }
}
