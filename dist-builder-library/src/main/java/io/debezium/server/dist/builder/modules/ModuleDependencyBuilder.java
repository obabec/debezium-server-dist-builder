package io.debezium.server.dist.builder.modules;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class ModuleDependencyBuilder {
    private final Document document;
    private String groupId;
    private String artifactId;
    private String version = "${version.debezium}";

    public ModuleDependencyBuilder(Document document) {
        this.document = document;
    }

    public ModuleDependencyBuilder setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public ModuleDependencyBuilder setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public ModuleDependencyBuilder setVersion(String version) {
        this.version = version;
        return this;
    }

    public Node buildDependency() {
        Node dependency = document.createElement("dependency");

        Node groupId = document.createElement("groupId");
        groupId.setTextContent(this.groupId);
        dependency.appendChild(groupId);

        Node artifactId = document.createElement("artifactId");
        artifactId.setTextContent(this.artifactId);
        dependency.appendChild(artifactId);

        if (this.version != null) {
            Node version = document.createElement("version");
            version.setTextContent(this.version);
            dependency.appendChild(version);
        }
        return dependency;
    }

}
