package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.SourceNodeConfiguration;
import io.debezium.server.dist.builder.modules.types.BigintHandlingMode;
import io.debezium.server.dist.builder.modules.types.BinaryHandlingMode;
import io.debezium.server.dist.builder.modules.types.FailureHandlingMode;
import io.debezium.server.dist.builder.modules.types.SchemaHandlingMode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;

public class MysqlSourceNode extends SourceNodeConfiguration {
    private final String ARTIFACT_ID = DEBEZIUM_CONNECTOR_PREFIX + "mysql";
    private final String connectorClass = "io.debezium.connector.mysql.MySqlConnector";
    private Integer databaseServerId;

    private BigintHandlingMode bigintUnsignedHandlingMode;

    private Boolean includeSchemaComments;

    private Boolean includeQuery;

    private FailureHandlingMode eventDeserializationFailureHandlingMode;

    private SchemaHandlingMode inconsistentSchemaHandlingMode;

    private Integer maxBatchSize;
    private Integer maxQueueSize;
    private Long maxQueueSizeInBytes;
    private Integer pollIntervalMs;
    private Integer connectTimeoutMs;
    private List<String> gtidSourceIncludes;
    private List<String> gtidSourceExcludes;


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
