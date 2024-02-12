import io.debezium.server.dist.builder.metadata.MetadataGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainClass {
        public static void main(String[] args) throws ClassNotFoundException, IOException {
               /* new DebeziumServerDistributionBuilder()
                        .withDebeziumServer(dbp)
                        .withVersion("2.3.2.Final")
                        .withLocalProject("/tmp/test-res/debezium-server-dist")
                        .build()
                        .generateConfigurationProperties()
                        .mavenBuild("/usr/local/Cellar/maven/3.9.4/libexec");*/

                MetadataGenerator metadataGenerator = new MetadataGenerator();
                metadataGenerator.generateMetadata(new FileOutputStream("metadata-js.json"));


        }
}
