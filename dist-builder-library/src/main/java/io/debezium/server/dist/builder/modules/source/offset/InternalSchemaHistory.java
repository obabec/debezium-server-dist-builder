package io.debezium.server.dist.builder.modules.source.offset;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesConfig;
import io.debezium.server.dist.builder.modules.source.storage.SchemaHistoryStorage;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Properties;

//@Buildable
@Getter
@Setter
@Buildable
public class InternalSchemaHistory implements PropertiesConfig, ModuleNode {
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
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        propertiesBuilder.put("debezium.source.schema.history.internal", schemaHistoryClass);
        propertiesBuilder.put("debezium.source.schema.history.internal.name", connectorName);
        propertiesBuilder.put("debezium.source.schema.history.internal.skip.unparseable.ddl", skipUnparseableDdl);
        propertiesBuilder.put("debezium.source.schema.history.internal.store.only.captured.tables.ddl", storeOnlyCapturedTablesDdl);
        propertiesBuilder.put("debezium.source.schema.history.internal.store.only.captured.databases.ddl", storeOnlyCapturedDatabasesDdl);
        propertiesBuilder.put("debezium.source.schema.history.internal.ddl.filter", storeOnlyCapturedDatabasesDdl);
        propertiesBuilder.put("debezium.source.schema.history.internal.connector.id", connectorId);
        propertiesBuilder.put("debezium.source.schema.history.internal.prefer.ddl", preferDdl);


        propertiesBuilder.putAllWithPrefix("debezium.source.schema.history.internal.", storageConfig);
        return propertiesBuilder.getProperties();
    }

    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return storageConfig.buildNode(document, dependencyList);
    }
}
