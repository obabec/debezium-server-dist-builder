/*
 * MIT License
 *
 * Copyright (c) [2024] [Ondrej Babec <ond.babec@gmail.com>]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE
 * ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.debezium.server.dist.builder.modules;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * ModuleDependencyBuilder offers methods that can prepare DOM Node for XML dependency.
 */
public class ModuleDependencyBuilder {
    public static String version = "${version.debezium}";
    private static final String groupId = "io.debezium";

    /**
     * Method builds dependency with default groupId and version. Both set to Debezium's default.
     * @param document DOM document (pom)
     * @param artifactId Specific artifactId for dependency
     * @param dependencyList Current list of dependencies (used to mitigate duplicates)
     * @return prepared dependency Node
     */
    public static Node buildDependency(Document document, String artifactId, List<Dependency> dependencyList) {
        Dependency newDep = new Dependency(groupId, artifactId, "");
        return getNode(document, artifactId, version, dependencyList, newDep);
    }

    /**
     * Method builds dependency with default groupId. Both set to Debezium's default.
     * @param document DOM document (pom)
     * @param artifactId Specific artifactId for dependency
     * @param dependencyList Current list of dependencies (used to mitigate duplicates)
     * @param newVersion Specific version for the dependency
     * @return prepared dependency Node
     */
    public static Node buildDependency(Document document, String artifactId, String newVersion, List<Dependency> dependencyList) {
        Dependency newDep = new Dependency(groupId, artifactId, newVersion);
        return getNode(document, artifactId, newVersion, dependencyList, newDep);
    }

    /**
     * Checks if Node exists in list and if not it creates specific dependency in DOM.
     * @param document DOM document (pom)
     * @param artifactId Specific artifactId for dependency
     * @param newVersion Specific version for the dependency
     * @param dependencyList Current list of dependencies (used to mitigate duplicates)
     * @param newDep Expected dependency in list
     * @return New node or NULL
     */
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

    /**
     * Builds fully qualified dependency node
     * @param document DOM document (pom)
     * @param customDependency fully specificed Dependency object
     * @param dependencyList Current list of dependencies (used to mitigate duplicates)
     * @return
     */
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
