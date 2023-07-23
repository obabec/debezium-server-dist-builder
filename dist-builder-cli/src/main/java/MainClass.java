import io.debezium.server.dist.builder.DebeziumServer;
import io.debezium.server.dist.builder.DebeziumServerBuilder;
import io.debezium.server.dist.builder.modules.config.sinks.types.RedisMessageFormat;

public class MainClass {
    DebeziumServer db = new DebeziumServerBuilder()
            .withNewOracleSourceNode()
                .withDatabaseDbname("test-dataabase")
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
            .build();
}
