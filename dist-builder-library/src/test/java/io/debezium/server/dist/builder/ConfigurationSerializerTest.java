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