package io.debezium.server.dist.builder.modules.source.storage;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Properties;

public class AzureBlobStorageConfig implements StorageConfig, SchemaHistoryStorage {
    private final String ARTIFACT_ID = "debezium-storage-azure-blob";

    private String storageAccountConnectionString;
    private String storageAccountContainerName;
    private String storageBlobName;


    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        propertiesBuilder.put("azure.storage.account.connectionstring", storageAccountConnectionString);
        propertiesBuilder.put("azure.storage.account.container.name", storageAccountContainerName);
        propertiesBuilder.put("azure.storage.blob.name", storageBlobName);
        return propertiesBuilder.getProperties();
    }
}
