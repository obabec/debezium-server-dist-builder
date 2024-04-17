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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ModuleDependencyBuilderTest {

    @Test
    void testBuildDependency() {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("base.xml")) {
            DocumentBuilder documentBuilderFactory = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
            Document doc = documentBuilderFactory.parse(is);
            List<Dependency> dependencyList = new ArrayList<>();
            Node node = ModuleDependencyBuilder.buildDependency(doc, "test", dependencyList);
            assertThat(dependencyList.size(), is(equalTo(1)));
            assertThat(node.getNodeName(), is(equalTo("dependency")));
            assertThat(node.getFirstChild().getNodeName(), is(equalTo("groupId")));
            assertThat(node.getFirstChild().getTextContent(), is(equalTo("io.debezium")));
            assertThat(node.getFirstChild().getNextSibling().getNodeName(), is(equalTo("artifactId")));
            assertThat(node.getFirstChild().getNextSibling().getTextContent(), is(equalTo("test")));
            assertThat(node.getFirstChild().getNextSibling().getNextSibling().getNodeName(), is(equalTo("version")));
            assertThat(node.getFirstChild().getNextSibling().getNextSibling().getTextContent(), is(equalTo("${version.debezium}")));

        } catch (IOException | SAXException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testBuildCustomDependency() {

        Dependency dependency = new Dependency("io.apache", "kafka", "3.7.0");

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("base.xml")) {
            DocumentBuilder documentBuilderFactory = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
            Document doc = documentBuilderFactory.parse(is);
            List<Dependency> dependencyList = new ArrayList<>();
            Node node = ModuleDependencyBuilder.buildDependency(doc, dependency, dependencyList);
            assertThat(dependencyList.size(), is(equalTo(1)));
            assertThat(dependencyList.get(0), is(equalTo(dependency)));
            assertThat(node.getNodeName(), is(equalTo("dependency")));
            assertThat(node.getFirstChild().getNodeName(), is(equalTo("groupId")));
            assertThat(node.getFirstChild().getTextContent(), is(equalTo("io.apache")));
            assertThat(node.getFirstChild().getNextSibling().getNodeName(), is(equalTo("artifactId")));
            assertThat(node.getFirstChild().getNextSibling().getTextContent(), is(equalTo("kafka")));
            assertThat(node.getFirstChild().getNextSibling().getNextSibling().getNodeName(), is(equalTo("version")));
            assertThat(node.getFirstChild().getNextSibling().getNextSibling().getTextContent(), is(equalTo("3.7.0")));

        } catch (IOException | SAXException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testBuildDependencyWithVersion() {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("base.xml")) {
            DocumentBuilder documentBuilderFactory = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
            Document doc = documentBuilderFactory.parse(is);
            List<Dependency> dependencyList = new ArrayList<>();
            Node node = ModuleDependencyBuilder.buildDependency(doc, "test", "1.0.1.alpha1", dependencyList);
            assertThat(dependencyList.size(), is(equalTo(1)));
            assertThat(node.getNodeName(), is(equalTo("dependency")));
            assertThat(node.getFirstChild().getNodeName(), is(equalTo("groupId")));
            assertThat(node.getFirstChild().getTextContent(), is(equalTo("io.debezium")));
            assertThat(node.getFirstChild().getNextSibling().getNodeName(), is(equalTo("artifactId")));
            assertThat(node.getFirstChild().getNextSibling().getTextContent(), is(equalTo("test")));
            assertThat(node.getFirstChild().getNextSibling().getNextSibling().getNodeName(), is(equalTo("version")));
            assertThat(node.getFirstChild().getNextSibling().getNextSibling().getTextContent(), is(equalTo("1.0.1.alpha1")));

        } catch (IOException | SAXException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}