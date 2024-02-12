package io.debezium.server.dist.builder.modules;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class ModuleDependencyBuilder {
    public static String version = "${version.debezium}";
    private static String groupId = "io.debezium";

    public static Node buildDependency(Document document, String artifactId) {
        Node dependency = buildBase(document, artifactId);

        Node versionNode = document.createElement("version");
        versionNode.setTextContent(version);
        dependency.appendChild(versionNode);
        return dependency;
    }

    public static Node buildDependency(Document document, String artifactId, String newVersion) {
        Node dependency = buildBase(document, artifactId);

        Node versionNode = document.createElement("version");
        versionNode.setTextContent(newVersion);
        dependency.appendChild(versionNode);

        return dependency;
    }

    private static Node buildBase(Document document, String artifactId) {
        Node dependency = document.createElement("dependency");

        Node groupIdNode = document.createElement("groupId");
        groupIdNode.setTextContent(groupId);
        dependency.appendChild(groupIdNode);

        Node artifactNode = document.createElement("artifactId");
        artifactNode.setTextContent(artifactId);
        dependency.appendChild(artifactNode);
        return dependency;
    }

}
