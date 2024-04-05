package io.debezium.server.dist.builder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import io.debezium.server.dist.builder.modules.Dependency;
import io.debezium.server.dist.builder.modules.config.sources.types.CaptureMode;
import io.debezium.server.dist.builder.modules.sink.Http;
import io.debezium.server.dist.builder.modules.source.Mongo;
import io.debezium.server.dist.builder.modules.source.offset.InternalSchemaHistory;
import io.debezium.server.dist.builder.modules.source.offset.OffsetStorage;
import io.debezium.server.dist.builder.modules.source.storage.FileStorage;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class DebeziumServerDistributionBuilderTest {
    private final String confPath = "/src/main/resources/distro/conf";
    private String prepareTempRepository() throws IOException {
        String temp = UUID.randomUUID().toString();
        String path = this.getClass().getResource("/").getPath() + "/" + temp + confPath;
        new File(path).mkdirs();
        new File(path + "/application.properties").createNewFile();
        Files.copy(Path.of(this.getClass().getClassLoader().getResource("base.xml").getPath()),
                Path.of(this.getClass().getClassLoader().getResource(temp).getPath() + "/pom.xml"));
        return temp;
    }

    private void deleteFolder(String name) {
        File folder = new File(this.getClass().getResource("/" + name).getPath());
        for (File file : folder.listFiles()) {
            file.delete();
        }
        folder.delete();
    }

    private CustomDebeziumServer prepareServer() {
        CustomDebeziumServer customDebeziumServer = new CustomDebeziumServer();
        Mongo mongo = new Mongo();
        mongo.setCaptureMode(CaptureMode.CHANGE_STREAMS);
        mongo.setName("test-mongo");
        mongo.setMongodbHosts(List.of("hostname"));
        Http http = new Http();
        http.setHttpRetries(5);
        http.setHttpUrl("http-url");
        customDebeziumServer.setSinkNode(http);
        customDebeziumServer.setSourceNode(mongo);
        InternalSchemaHistory sh = new InternalSchemaHistory();
        sh.setConnectorId("1");
        sh.setSchemaHistoryClass("schema.class");
        FileStorage fileStorage = new FileStorage();
        fileStorage.setFileFilename("file-name");
        sh.setStorageConfig(fileStorage);
        OffsetStorage offsetStorage = new OffsetStorage();
        offsetStorage.setStorageConfig(fileStorage);
        offsetStorage.setOffsetStorage("offset-storage");
        customDebeziumServer.setOffsetStorage(offsetStorage);
        customDebeziumServer.setInternalSchemaHistory(sh);
        Dependency dependency = new Dependency();
        dependency.setArtifactId("aid");
        dependency.setGroupId("io.ha");
        dependency.setComment("Comment");
        dependency.setVersion("1.0.0");
        customDebeziumServer.setDependencyList(List.of(dependency));
        return customDebeziumServer;
    }


    @Test
    void build() throws IOException {
        CustomDebeziumServer customDebeziumServer = prepareServer();
        DebeziumServerDistributionBuilder builder = new DebeziumServerDistributionBuilder();
        String testRepo = prepareTempRepository();
        builder.withDebeziumServer(customDebeziumServer).withLocalProject(this.getClass().getResource("/" + testRepo).getPath());
        builder.build();
        InputStream ais = this.getClass().getResource("/" + testRepo + "/pom.xml").openConnection().getInputStream();
        InputStream eis = this.getClass().getClassLoader().getResourceAsStream("built-pom.xml");
        assertThat(ais.readAllBytes(), is(equalTo(eis.readAllBytes())));
        deleteFolder(testRepo);
    }

    @Test
    void generateConfigurationProperties() throws IOException {
        CustomDebeziumServer customDebeziumServer = prepareServer();
        DebeziumServerDistributionBuilder builder = new DebeziumServerDistributionBuilder();
        String testRepo = prepareTempRepository();
        builder.withDebeziumServer(customDebeziumServer).withLocalProject(this.getClass().getResource("/" + testRepo).getPath());
        builder.generateConfigurationProperties();
        InputStream ais = this.getClass().getResource("/" + testRepo + confPath + "/application.properties").openConnection().getInputStream();
        InputStream eis = this.getClass().getClassLoader().getResourceAsStream("built-properties.properties");
        assertThat(ais.readAllBytes(), is(equalTo(eis.readAllBytes())));
        deleteFolder(testRepo);
    }

    @Test
    void generateOperatorCR() throws IOException {
        CustomDebeziumServer customDebeziumServer = prepareServer();
        DebeziumServerDistributionBuilder builder = new DebeziumServerDistributionBuilder();
        String testRepo = prepareTempRepository();
        builder.withDebeziumServer(customDebeziumServer).withLocalProject(this.getClass().getResource("/" + testRepo).getPath());
        builder.generateOperatorCR();
        InputStream ais = this.getClass().getResource("/" + testRepo + "/010_custom-debezium-server.yaml" ).openConnection().getInputStream();
        InputStream eis = this.getClass().getClassLoader().getResourceAsStream("generated-cr.yaml");
        assertThat(ais.readAllBytes(), is(equalTo(eis.readAllBytes())));
        deleteFolder(testRepo);
    }
}