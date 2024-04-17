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

package io.debezium.server.dist.builder.modules.config.sources.types;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import io.debezium.server.dist.builder.modules.config.YamlConfig;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SchemaHistoryInternalConfig implements PropertiesConfig, YamlConfig {

    protected String kafkaTopic;

    protected List<String> kafkaBootstrapServers;

    protected Integer kafkaRecoveryPollIntervalMs;

    protected Integer kafkaQueryTimeoutMs;

    protected Integer kafkaCreateTimeoutMs;

    protected Integer kafkaRecoveryAttempts;

    protected Boolean skipUnparseableDdl;

    protected Boolean storeOnlyCapturedTablesDdl;

    protected Boolean storeOnlyCapturedDatabasesDdl;

    public SchemaHistoryInternalConfig() {
    }

    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        String PREFIX = "debezium.source.schema.history.internal";
        builder.put(PREFIX + "kafka.topic", kafkaTopic);
        builder.putList(PREFIX + "kafka.bootstrap.servers", kafkaBootstrapServers);
        builder.put(PREFIX + "kafka.recovery.poll.interval.ms", kafkaRecoveryPollIntervalMs);
        builder.put(PREFIX + "kafka.query.timeout.ms", kafkaQueryTimeoutMs);
        builder.put(PREFIX + "kafka.create.timeout.ms", kafkaCreateTimeoutMs);
        builder.put(PREFIX + "kafka.recovery.attempts", kafkaRecoveryAttempts);
        builder.putBoolean(PREFIX + "skip.unparseable.ddl", skipUnparseableDdl);
        builder.putBoolean(PREFIX + "store.only.captured.ddl", storeOnlyCapturedTablesDdl);
        builder.putBoolean(PREFIX + "store.only.captured.databases.ddl", storeOnlyCapturedDatabasesDdl);
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        getCommonConfig(propertiesBuilder);
        return propertiesBuilder.getProperties();
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getCommonConfig(yamlBuilder);
        return yamlBuilder.getYaml();
    }
}
