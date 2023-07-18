package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.SourceNodeConfiguration;
import io.debezium.server.dist.builder.modules.types.BinaryHandlingMode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;

public class SqlServerSourceNode extends SourceNodeConfiguration {
    private final String ARTIFACT_ID = DEBEZIUM_CONNECTOR_PREFIX + "sql";
    private final String connectorClass = "io.debezium.connector.sqlserver.SqlServerConnector";
    private String databaseInstance;

    private List<String> databaseNames;

    private List<String> schemaIncludeList;
    private List<String> schemaExcludeList;





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
