package io.debezium.server.dist.builder;

public class MainClass {

    public static void main(String[] args) {
        DebeziumServerAssemblyBuilder pomBuilder = new DebeziumServerAssemblyBuilder()
                .addKafkaSink()
                .addPulsarSink()
                .addKafkaSink()
                .addMysqlSource()
                .addPostgresSource();

        //new ProjectBuilder("/Users/obabec/Desktop/test-debezium-server")
          //      .cloneDistFolder()
            //    .removeCurrentPom()
              //  .buildProject(pomBuilder, true);
    }
}
