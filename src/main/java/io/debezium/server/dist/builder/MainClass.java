package io.debezium.server.dist.builder;

public class MainClass {

    public static void main(String[] args) {
        DebeziumServerPomBuilder pomBuilder = new DebeziumServerPomBuilder()
                .addKafkaSink()
                .addPulsarSink()
                .addKafkaSink()
                .addMysqlSource()
                .addPostgresSource()
                .build();

        new ProjectBuilder("/Users/obabec/Desktop/test-debezium-server")
                .cloneDistFolder()
                .removeCurrentPom()
                .buildProject(pomBuilder, true);
    }
}
