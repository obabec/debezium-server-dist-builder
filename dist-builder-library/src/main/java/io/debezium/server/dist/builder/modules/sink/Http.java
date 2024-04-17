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

package io.debezium.server.dist.builder.modules.sink;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


@Buildable
@Getter
@Setter
public class Http implements SinkNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "http";

    private final String type = "http";

    private String httpUrl;

    private Integer httpTimeoutMs;

    private Integer httpRetries;

    private Integer httpRetryIntervalMs;

    private String httpHeadersPrefix;

    private Boolean httpHeadersEnableBase64;

    private String httpAuthenticationType;

    private String httpAuthenticationJwtUsername;
    private String httpAuthenticationJwtPassword;

    private String httpAuthenticationJwtUrl;

    private Integer httpAuthenticationJwtTokenExpiration;

    private Integer httpAuthenticationJwtRefreshTokenExpiration;

    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put(SINK_NODE_CONFIG_PREFIX + "http.url", httpUrl);
        builder.put(SINK_NODE_CONFIG_PREFIX + "http.timeout.ms", httpTimeoutMs);
        builder.put(SINK_NODE_CONFIG_PREFIX + "http.retries", httpRetries);
        builder.put(SINK_NODE_CONFIG_PREFIX + "http.retry.interval.ms", httpRetryIntervalMs);
        builder.put(SINK_NODE_CONFIG_PREFIX + "http.headers.prefix", httpHeadersPrefix);
        builder.putBoolean(SINK_NODE_CONFIG_PREFIX + "http.headers.encode.base64", httpHeadersEnableBase64);
        builder.put(SINK_NODE_CONFIG_PREFIX + "http.authentication.type", httpAuthenticationType);
        builder.put(SINK_NODE_CONFIG_PREFIX + "http.authentication.jwt.username", httpAuthenticationJwtUsername);
        builder.put(SINK_NODE_CONFIG_PREFIX + "http.authentication.jwt.password", httpAuthenticationJwtPassword);
        builder.put(SINK_NODE_CONFIG_PREFIX + "http.authentication.jwt.url", httpAuthenticationJwtUrl);
        builder.put(SINK_NODE_CONFIG_PREFIX + "http.authentication.jwt.token_expiration", httpAuthenticationJwtTokenExpiration);
        builder.put(SINK_NODE_CONFIG_PREFIX + "http.authentication.jwt.refresh_token_expiration", httpAuthenticationJwtRefreshTokenExpiration);
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getCommonConfig(yamlBuilder);
        return yamlBuilder.getYaml();
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "type", type);
        getCommonConfig(propertiesBuilder);
        return propertiesBuilder.getProperties();
    }
}
