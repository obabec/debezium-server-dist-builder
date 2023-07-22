package io.debezium.server.dist.builder.modules.source;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.config.SqlBasedConnectorConfig;
import io.debezium.server.dist.builder.modules.config.types.SchemaHistoryInternalConfig;
import io.debezium.server.dist.builder.modules.config.types.SnapshotIsolationMode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class Db2SourceNode extends SqlBasedConnectorConfig implements ModuleNode {
    private final String ARTIFACT_ID = DEBEZIUM_CONNECTOR_PREFIX + "db2";

    private final String connectorClass = "io.debezium.connector.db2.Db2Connector";

    private SnapshotIsolationMode snapshotIsolationMode;

    private Integer pollIntervalMs;

    private SchemaHistoryInternalConfig schemaHistoryInternalConfig;


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
