package io.debezium.server.dist.builder.modules.source.storage.jdbc;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.source.storage.SchemaHistoryStorage;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Properties;

@Getter
@Setter
@Buildable
public class JdbcSchemaHistory extends JdbcBaseStorage implements SchemaHistoryStorage {

    private String schemaHistoryTableName;

    private String schemaHistoryTableDdl;

    private String schemaHistoryTableSelect;

    private String schemaHistoryTableInsert;

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        propertiesBuilder.put("jdbc.schema.history.table.name", schemaHistoryTableName);
        propertiesBuilder.put("jdbc.schema.history.table.ddl", schemaHistoryTableDdl);
        propertiesBuilder.put("jdbc.schema.history.table.select", schemaHistoryTableSelect);
        propertiesBuilder.put("jdbc.schema.history.table.insert", schemaHistoryTableInsert);
        return propertiesBuilder.getProperties();
    }

    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }
}
