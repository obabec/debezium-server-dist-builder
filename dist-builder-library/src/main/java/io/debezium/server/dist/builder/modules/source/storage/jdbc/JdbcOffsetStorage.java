package io.debezium.server.dist.builder.modules.source.storage.jdbc;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.source.storage.StorageConfig;
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
public class JdbcOffsetStorage extends JdbcBaseStorage implements StorageConfig {
    private String offsetTableName;
    private String offsetTableDdl;

    private String offsetTableSelect;
    private String offsetTableDelete;
    private String offsetTableInsert;

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        propertiesBuilder.put("jdbc.offset.table.name", offsetTableName);
        propertiesBuilder.put("jdbc.offset.table.ddl", offsetTableDdl);
        propertiesBuilder.put("jdbc.offset.table.select", offsetTableSelect);
        propertiesBuilder.put("jdbc.offset.table.delete", offsetTableDelete);
        propertiesBuilder.put("jdbc.offset.table.insert", offsetTableInsert);
        return propertiesBuilder.getProperties();
    }

    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }
}
