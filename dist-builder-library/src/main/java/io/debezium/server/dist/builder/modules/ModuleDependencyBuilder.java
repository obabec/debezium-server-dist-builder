package io.debezium.server.dist.builder.modules;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class ModuleDependencyBuilder {
    public static String version = "${version.debezium}";
    private static final String groupId = "io.debezium";

    public static Node buildDependency(Document document, String artifactId, List<Dependency> dependencyList) {
        Dependency newDep = new Dependency(groupId, artifactId, "");
        return getNode(document, artifactId, version, dependencyList, newDep);
    }

    public static Node buildDependency(Document document, String artifactId, String newVersion, List<Dependency> dependencyList) {
        Dependency newDep = new Dependency(groupId, artifactId, newVersion);
        return getNode(document, artifactId, newVersion, dependencyList, newDep);
    }

    private static Node getNode(Document document, String artifactId, String newVersion, List<Dependency> dependencyList, Dependency newDep) {
        if (!dependencyList.contains(newDep)) {
            Node dependency = buildBase(document, artifactId);

            Node versionNode = document.createElement("version");
            versionNode.setTextContent(newVersion);
            dependency.appendChild(versionNode);
            dependencyList.add(newDep);
            return dependency;
        }
        return null;
    }

    public static Node buildDependency(Document document, Dependency customDependency, List<Dependency> dependencyList) {
        if (!dependencyList.contains(customDependency)) {
            Node dependency = document.createElement("dependency");
            if (customDependency.getComment() != null && !customDependency.getComment().isEmpty()) {
                dependency.appendChild(document.createComment(customDependency.getComment()));
            }

            Node groupIdNode = document.createElement("groupId");
            groupIdNode.setTextContent(customDependency.getGroupId());
            dependency.appendChild(groupIdNode);

            Node artifactNode = document.createElement("artifactId");
            artifactNode.setTextContent(customDependency.getArtifactId());
            dependency.appendChild(artifactNode);

            Node versionNode = document.createElement("version");
            versionNode.setTextContent(customDependency.getVersion());
            dependency.appendChild(versionNode);
            dependencyList.add(customDependency);
            return dependency;
        }
        return null;
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
