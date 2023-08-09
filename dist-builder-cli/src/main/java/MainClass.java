import io.debezium.server.dist.builder.DebeziumServer;
import io.debezium.server.dist.builder.DebeziumServerBuilder;
import io.debezium.server.dist.builder.DebeziumServerDistributionBuilder;
import io.debezium.server.dist.builder.modules.config.sinks.types.RedisMessageFormat;

public class MainClass {
        public static void main(String[] args) {
                DebeziumServer dbp = new DebeziumServerBuilder()
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
                                        .withFlushIntervalMs(500)
                                .endFileStorageConfig()
                        .endOffsetStorage()
                        .withNewInternalSchemaHistory()
                                .withNewFileStorageConfig()
                                        .withFileFilename("internal.txt")
                                        .withFlushIntervalMs(900)
                                .endFileStorageConfig()
                                .withSchemaHistoryClass("io.debezium.storage.file.history.FileSchemaHistory")
                        .endInternalSchemaHistory()
                        .build();

                new DebeziumServerDistributionBuilder()
                        .withDebeziumServer(dbp)
                        .withLocalProject("/tmp/test-res/debezium-server-dist")
                        .build()
                        .generateConfigurationProperties()
                        .mavenBuild("/usr/local/Cellar/maven/3.9.4/libexec");
        }
}
