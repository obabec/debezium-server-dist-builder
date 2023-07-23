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


/*
* Separatni modul - server distribution
* tento modul bude obsahovat veskere buildeni Nodu - buildery se vygeneruji Sundriem
*
* Dalsi modul uz bude PomDistributionBuilder
* Ten vezme ServerDistribution zbuildeny pomoci generovanych builderu a sestavi POM + properties file
*
* Treti modul bude operator CrdBuilder, ktery namisto POM a properties vytvori CRD pro DebeziumServerOperator
*
* CLI a Web UI budou pracovat s obema moduly
*
*
*
*
*
* */