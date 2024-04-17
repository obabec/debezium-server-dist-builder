# debezium-server-dist-builder
This library allows to create specific Debezium server distribution with custom combination of sources and sinks.

## Example usage
Preparing the CustomDebeziumServer object with all configuration.
```java
CustomDebeziumServer dbp = new CustomDebeziumServerBuilder()
        .withNewOracleSourceNode()
                .withDatabaseDbname("test-database")
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
```
Building the distribution
```java
DebeziumServerDistributionBuilder builder = new DebeziumServerDistributionBuilder()
    .withDebeziumServer(dbp)
    .withVersion("2.3.2.Final")
    .withDefaultRepository("/tmp/server")
    .build()
    .generateConfigurationProperties()
    .generateOperatorCR();
```

## Requirements
Before using the library you need to build all the Sundrio `Builder` objects using `mvn clean install`.

## Tests
Run the tests with `mvn verify`.