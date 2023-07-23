package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Properties;



@Buildable
@Getter
@Setter
public class Http implements SinkNode, PropertiesConfig {
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
    public Node buildNode(Document document) {
        return new ModuleDependencyBuilder(document)
                .setGroupId(GROUP_ID)
                .setArtifactId(ARTIFACT_ID)
                .buildDependency();
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }


    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();

        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "type", type);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "http.url", httpUrl);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "http.timeout.ms", httpTimeoutMs);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "http.retries", httpRetries);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "http.retry.interval.ms", httpRetryIntervalMs);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "http.headers.prefix", httpHeadersPrefix);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "http.headers.encode.base64", httpHeadersEnableBase64.toString());
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "http.authentication.type",httpAuthenticationType);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "http.authentication.jwt.username",httpAuthenticationJwtUsername);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "http.authentication.jwt.password",httpAuthenticationJwtPassword);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "http.authentication.jwt.url",httpAuthenticationJwtUrl);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "http.authentication.jwt.token_expiration",httpAuthenticationJwtTokenExpiration);
        propertiesBuilder.put(SINK_NODE_CONFIG_PREFIX + "http.authentication.jwt.refresh_token_expiration",httpAuthenticationJwtRefreshTokenExpiration);


        return propertiesBuilder.getProperties();
    }
}
