package io.debezium.server.dist.builder.modules.source.storage;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@Getter
@Setter
@Buildable
public class S3StorageConfig implements SchemaHistoryStorage {
    private final String ARTIFACT_ID = "debezium-storage-s3";

    private String accessKeyId;
    private String secretAccessKey;

    private String regionName;

    private String bucketName;

    private String objectName;

    private String endpoint;


    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    public <C extends Config> void getCommonConfig(ConfigBuilder<C> builder) {
        builder.put("s3.access.key.id", accessKeyId);
        builder.put("s3.secret.access.key", secretAccessKey);
        builder.put("s3.region.name", regionName);
        builder.put("s3.bucket.name", bucketName);
        builder.put("s3.object.name", objectName);
        builder.put("s3.endpoint", endpoint);
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getCommonConfig(yamlBuilder);
        return yamlBuilder.getYaml();
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        getCommonConfig(propertiesBuilder);
        return propertiesBuilder.getProperties();
    }


}
