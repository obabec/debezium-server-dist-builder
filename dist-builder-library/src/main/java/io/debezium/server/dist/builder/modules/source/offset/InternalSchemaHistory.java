package io.debezium.server.dist.builder.modules.source.offset;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import io.debezium.server.dist.builder.modules.config.YamlConfig;
import io.debezium.server.dist.builder.modules.source.storage.SchemaHistoryStorage;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

//@Buildable
@Getter
@Setter
@Buildable
public class InternalSchemaHistory implements PropertiesConfig, YamlConfig, ModuleNode {
    private String schemaHistoryClass;
    private String connectorName;
    private String connectorId;
    private Boolean skipUnparseableDdl;
    private Boolean storeOnlyCapturedTablesDdl;
    private Boolean storeOnlyCapturedDatabasesDdl;
    private String ddlFilter;
    private Boolean preferDdl;

    private SchemaHistoryStorage storageConfig;


    @Override
    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put("debezium.source.schema.history.internal", schemaHistoryClass);
        builder.put("debezium.source.schema.history.internal.name", connectorName);
        builder.put("debezium.source.schema.history.internal.skip.unparseable.ddl", skipUnparseableDdl);
        builder.put("debezium.source.schema.history.internal.store.only.captured.tables.ddl", storeOnlyCapturedTablesDdl);
        builder.put("debezium.source.schema.history.internal.store.only.captured.databases.ddl", storeOnlyCapturedDatabasesDdl);
        builder.put("debezium.source.schema.history.internal.ddl.filter", storeOnlyCapturedDatabasesDdl);
        builder.put("debezium.source.schema.history.internal.connector.id", connectorId);
        builder.put("debezium.source.schema.history.internal.prefer.ddl", preferDdl);
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getCommonConfig(yamlBuilder);
        yamlBuilder.putAllWithPrefix("debezium.source.schema.history.internal.", storageConfig);
        return yamlBuilder.getYaml();
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        getCommonConfig(propertiesBuilder);
        propertiesBuilder.putAllWithPrefix("debezium.source.schema.history.internal.", storageConfig);
        return propertiesBuilder.getProperties();
    }

    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return storageConfig.buildNode(document, dependencyList);
    }
}
