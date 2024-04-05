package io.debezium.server.dist.builder.modules.source.storage.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import io.debezium.server.dist.builder.modules.source.storage.SchemaHistoryStorage;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@Getter
@Setter
@Buildable
public class JdbcSchemaHistory extends JdbcBaseStorage implements SchemaHistoryStorage {

    private String schemaHistoryTableName;

    private String schemaHistoryTableDdl;

    private String schemaHistoryTableSelect;

    private String schemaHistoryTableInsert;

    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put("jdbc.schema.history.table.name", schemaHistoryTableName);
        builder.put("jdbc.schema.history.table.ddl", schemaHistoryTableDdl);
        builder.put("jdbc.schema.history.table.select", schemaHistoryTableSelect);
        builder.put("jdbc.schema.history.table.insert", schemaHistoryTableInsert);
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder(super.toYaml());
        getCommonConfig(yamlBuilder);
        return yamlBuilder.getYaml();
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        getCommonConfig(propertiesBuilder);
        return propertiesBuilder.getProperties();
    }

    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }
}
