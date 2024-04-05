package io.debezium.server.dist.builder;

import java.util.Arrays;
import java.util.List;

import io.debezium.server.dist.builder.modules.config.sources.types.CaptureMode;
import io.debezium.server.dist.builder.modules.sink.Http;
import io.debezium.server.dist.builder.modules.source.Mongo;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ConfigurationSerializerTest {

    @Test
    void configToPropertiesFile() {
        CustomDebeziumServer customDebeziumServer = new CustomDebeziumServer();
        Mongo mongo = new Mongo();
        mongo.setCaptureMode(CaptureMode.CHANGE_STREAMS);
        mongo.setName("test-mongo");
        mongo.setMongodbHosts(List.of("hostname"));
        Http http = new Http();
        http.setHttpRetries(5);
        http.setHttpUrl("http-url");
        customDebeziumServer.setSinkNode(http);
        customDebeziumServer.setSourceNode(mongo);
        String res = ConfigurationSerializer.configToPropertiesFile(customDebeziumServer);
        String expected = "debezium.sink.http.url=http-url\n" +
                "debezium.sink.http.retries=5\n" +
                "debezium.source.mongodb.hosts=hostname\n" +
                "debezium.sink.type=http\n" +
                "debezium.source.connector.class=io.debezium.connector.mongodb.MongoDbConnector\n" +
                "debezium.source.name=test-mongo\n" +
                "debezium.source.capture.mode=change_streams\n";
        assertThat(res, is(equalTo(expected)));
    }
}