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

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.debezium.operator.api.model.ConfigProperties;
import io.debezium.operator.api.model.DebeziumServerBuilder;
import io.debezium.operator.api.model.SinkBuilder;
import io.debezium.operator.api.model.SourceBuilder;
import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.config.YamlConfig;
import io.debezium.server.dist.builder.modules.source.offset.InternalSchemaHistory;
import io.debezium.server.dist.builder.modules.source.offset.OffsetStorage;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


@Getter
@Setter
@Buildable
public class CustomDebeziumServer implements PropertiesConfig, YamlConfig {
    private String version;
    private SourceNode sourceNode;
    private SinkNode sinkNode;

    // Generic server config
    private InternalSchemaHistory internalSchemaHistory;
    private OffsetStorage offsetStorage;
    private List<Dependency> dependencyList;

    @JsonIgnore
    @NonNull
    private io.debezium.operator.api.model.DebeziumServer operatorCR;

    public CustomDebeziumServer() {
        operatorCR = new DebeziumServerBuilder()
                .withNewMetadata()
                .withName("custom-debezium-server")
                .endMetadata()
                .withNewSpec()
                .withImage("IMAGE_PLACEHOLDER")
                .endSpec().build();
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder builder = new PropertiesBuilder();
        builder.putAll(sourceNode);
        builder.putAll(sinkNode);
        builder.putAll(internalSchemaHistory);
        builder.putAll(offsetStorage);
        return builder.getProperties();
    }

    @Override
    public HashMap<String, Object> toYaml() {
        if (operatorCR != null) {
            HashMap<String, Object> source = new HashMap<>(internalSchemaHistory.toYaml());
            source.putAll(offsetStorage.toYaml());
            source.putAll(sourceNode.toYaml());
            ConfigProperties sinkProps = new ConfigProperties();
            sinkProps.setAllProps(sinkNode.toYaml());
            SinkBuilder sinkBuilder = new SinkBuilder()
                    .withType(sinkNode.getType())
                    .withConfig(sinkProps);

            ConfigProperties sourceProps = new ConfigProperties();
            sourceProps.setAllProps(source);
            SourceBuilder sourceBuilder = new SourceBuilder()
                    .withSourceClass(sourceNode.getConnectorClass())
                    .withConfig(sourceProps);

            operatorCR.getSpec().setSink(sinkBuilder.build());
            operatorCR.getSpec().setSource(sourceBuilder.build());
            return new HashMap<>(operatorCR.getSpec().asConfiguration().getAsMap());
        }
        return null;
    }
}
