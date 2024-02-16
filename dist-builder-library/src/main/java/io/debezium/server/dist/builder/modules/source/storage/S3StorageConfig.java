package io.debezium.server.dist.builder.modules.source.storage;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
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

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder();
        propertiesBuilder.put("s3.access.key.id", accessKeyId);
        propertiesBuilder.put("s3.secret.access.key", secretAccessKey);
        propertiesBuilder.put("s3.region.name", regionName);
        propertiesBuilder.put("s3.bucket.name", bucketName);
        propertiesBuilder.put("s3.object.name", objectName);
        propertiesBuilder.put("s3.endpoint", endpoint);
        return propertiesBuilder.getProperties();
    }
}
