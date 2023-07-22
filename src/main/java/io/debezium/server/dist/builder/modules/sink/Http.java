package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Http implements ModuleNode {
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

    private Integer httpAuthenticationJwtToken_Expiration;

    private Integer httpAuthenticationJwtRefresh_token_expiration;

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


}
