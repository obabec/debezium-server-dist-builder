package io.debezium.server.dist.builder.modules.sink;

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

import java.util.HashMap;
import java.util.List;
import java.util.Properties;


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

    @Override
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
