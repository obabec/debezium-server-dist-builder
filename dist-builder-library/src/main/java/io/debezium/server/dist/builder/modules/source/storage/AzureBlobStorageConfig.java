package io.debezium.server.dist.builder.modules.source.storage;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class AzureBlobStorageConfig implements StorageConfig, SchemaHistoryStorage {
    private final String ARTIFACT_ID = "debezium-storage-azure-blob";

    private String storageAccountConnectionString;
    private String storageAccountContainerName;
    private String storageBlobName;

    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put("azure.storage.account.connectionstring", storageAccountConnectionString);
        builder.put("azure.storage.account.container.name", storageAccountContainerName);
        builder.put("azure.storage.blob.name", storageBlobName);
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getCommonConfig(yamlBuilder);
        return yamlBuilder.getYaml();
    }

    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        getCommonConfig(propertiesBuilder);
        return propertiesBuilder.getProperties();
    }


}
