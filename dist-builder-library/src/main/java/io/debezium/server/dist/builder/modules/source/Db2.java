package io.debezium.server.dist.builder.modules.source;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.config.Config;
import io.debezium.server.dist.builder.modules.config.ConfigBuilder;
import io.debezium.server.dist.builder.modules.config.PropertiesBuilder;
import io.debezium.server.dist.builder.modules.config.YamlBuilder;
import io.debezium.server.dist.builder.modules.config.sources.SqlBasedConnectorConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.SchemaHistoryInternalConfig;
import io.debezium.server.dist.builder.modules.config.sources.types.SnapshotIsolationMode;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


@Buildable
@Getter
@Setter
public class Db2 extends SqlBasedConnectorConfig implements SourceNode {
    private final String ARTIFACT_ID = DEBEZIUM_CONNECTOR_PREFIX + "db2";

    private final String connectorClass = "io.debezium.connector.db2.Db2Connector";

    private SnapshotIsolationMode snapshotIsolationMode;

    private Integer pollIntervalMs;

    public Db2() {
    }


    @Override
    public Node buildNode(Document document, List<Dependency> dependencyList) {
        return ModuleDependencyBuilder.buildDependency(document, ARTIFACT_ID, dependencyList);
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }

    public <C extends Config> void getDb2CommonConfig(ConfigBuilder<C> builder) {
        builder.putEnum(debeziumServerSourcePrefix + "snapshot.isolation.mode", snapshotIsolationMode);
        builder.put(debeziumServerSourcePrefix + "poll.interval.ms", pollIntervalMs);
    }

    @Override
    public HashMap<String, Object> toYaml() {
        YamlBuilder yamlBuilder = new YamlBuilder();
        getDb2CommonConfig(yamlBuilder);
        yamlBuilder.putAll(schemaHistoryInternalConfig);
        return yamlBuilder.getYaml();
    }

    @Override
    public Properties toProperties() {
        PropertiesBuilder propertiesBuilder = new PropertiesBuilder(super.toProperties());
        propertiesBuilder.put(debeziumServerSourcePrefix + "connector.class", connectorClass);
        getDb2CommonConfig(propertiesBuilder);
        propertiesBuilder.putAll(schemaHistoryInternalConfig);
        return propertiesBuilder.getProperties();
    }
}
