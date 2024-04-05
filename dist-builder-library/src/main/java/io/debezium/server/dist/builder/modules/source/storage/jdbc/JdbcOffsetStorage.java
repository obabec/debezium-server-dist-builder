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
import io.debezium.server.dist.builder.modules.source.storage.StorageConfig;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@Getter
@Setter
@Buildable
public class JdbcOffsetStorage extends JdbcBaseStorage implements StorageConfig {
    private String offsetTableName;
    private String offsetTableDdl;

    private String offsetTableSelect;
    private String offsetTableDelete;
    private String offsetTableInsert;

    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put("jdbc.offset.table.name", offsetTableName);
        builder.put("jdbc.offset.table.ddl", offsetTableDdl);
        builder.put("jdbc.offset.table.select", offsetTableSelect);
        builder.put("jdbc.offset.table.delete", offsetTableDelete);
        builder.put("jdbc.offset.table.insert", offsetTableInsert);
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
