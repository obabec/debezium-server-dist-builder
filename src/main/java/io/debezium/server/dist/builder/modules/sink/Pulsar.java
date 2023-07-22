package io.debezium.server.dist.builder.modules.sink;

import io.debezium.server.dist.builder.modules.ModuleDependencyBuilder;
import io.debezium.server.dist.builder.modules.ModuleNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Properties;

public class Pulsar implements ModuleNode {
    private final String ARTIFACT_ID = DEBEZIUM_SERVER_PREFIX + "pulsar";

    private final String type = "pulsar";

    private Integer pulsarTimeout;

    private Properties pulsarClient;
    private Properties pulsarProducer;

    private String pulsarNullKey;

    private String pulsarTenant;

    private String pulsarNamespace;

    private String ioDebeziumServerStream_Name_Mapper;

    @Override
    public Node buildNode(Document document) {
        return new ModuleDependencyBuilder(document)
                .setGroupId(GROUP_ID)
                .setArtifactId(ARTIFACT_ID)
                .buildDependency();
    }

    @Override
    public String toString() {
        return ARTIFACT_ID;
    }


}
