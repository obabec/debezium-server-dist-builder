import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.server.dist.builder.DebeziumServer;
import org.reflections.Reflections;

import javax.json.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class MainClass {
        public static void main(String[] args) throws ClassNotFoundException, IOException {
                /*DebeziumServer dbp = new DebeziumServerBuilder()
                        .withNewOracleSourceNode()
                                .withDatabaseDbname("test-dataabasasdasdasdase")
                                .withDatabaseUrl("testurl")
                                .withDatabasePassword("pass")
                                .withDatabaseUser("test-user")
                        .endOracleSourceNode()
                        .withNewRedisSinkNode()
                                .withRedisAddress("redis-address")
                                .withRedisBatchSize(3584)
                                .withRedisPassword("redis-pass")
                                .withRedisSslEnabled(true)
                                .withRedisMessageFormat(RedisMessageFormat.EXTENDED)
                        .endRedisSinkNode()
                        .withNewOffsetStorage()
                                .withOffsetStorage("io.debezium.storage.redis.offset.RedisOffsetBackingStore")
                                .withNewFileStorageConfig()
                                        .withFileFilename("testFile.txt")
                                        .withFlushIntervalMs(500)
                                .endFileStorageConfig()
                        .endOffsetStorage()§§§§§§§§§§§§§
                        .withNewInternalSchemaHistory()
                                .withNewFileStorageConfig()
                                        .withFileFilename("internal.txt")
                                        .withFlushIntervalMs(900)
                                .endFileStorageConfig()
                                .withSchemaHistoryClass("io.debezium.storage.file.history.FileSchemaHistory")
                        .endInternalSchemaHistory()
                        .build();

                new DebeziumServerDistributionBuilder()
                        .withDebeziumServer(dbp)
                        .withVersion("2.3.2.Final")
                        .withLocalProject("/tmp/test-res/debezium-server-dist")
                        .build()
                        .generateConfigurationProperties()
                        .mavenBuild("/usr/local/Cellar/maven/3.9.4/libexec");*/



                MetadataModelObject metadataModelObject = new MetadataModelObject();


                printFields(DebeziumServer.class, metadataModelObject);
                int x = 5;
                ObjectMapper mapper = new ObjectMapper();
                OutputStream outputStream = new FileOutputStream("test.txt");
                mapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, metadataModelObject);
        }

        private static void processInterfaceClass(Class c, MetadataModelObject metadataModelObject) throws ClassNotFoundException {
                Reflections reflections = new Reflections("io.debezium");
                Object[] listino =  reflections.getSubTypesOf(c).toArray();
                List<MetadataModelObject> arr = new ArrayList<>();

                for (Object o : listino) {
                        MetadataModelObject mo = new MetadataModelObject();
                        if (((Class) o).getName().contains("Editable")) {
                                continue;
                        }
                        if (((Class) o).getName().toString().contains("io.debezium")) {
                                mo.setType("class");
                        }

                        printFields(((Class) o), mo);

                        arr.add(mo);
                }

                metadataModelObject.setOptions(arr);
        }


        private static void printFields(Class c, MetadataModelObject metadataModelObject) throws ClassNotFoundException {
                metadataModelObject.setClazz(c.getTypeName());

                if (c.isInterface()) {
                        metadataModelObject.setType("interface");
                        processInterfaceClass(c, metadataModelObject);
                        return;
                }
                // Enum
                if (c.isEnum()) {
                        metadataModelObject.setType("enum");
                        List<String> variants = new ArrayList<>();
                        for (Object s : c.getEnumConstants()) {
                                variants.add(s.toString());
                        }
                        metadataModelObject.setVariants(variants);
                        return;
                }

                List<MetadataModelObject> fields = new ArrayList<>();

                List<Field> currentFields = new ArrayList<>();
                Class clazz = c;
                while (clazz != Object.class) {
                        currentFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
                        clazz = clazz.getSuperclass();
                }


                for (Field f : currentFields) {
                        if (Modifier.isFinal(f.getModifiers())) continue;
                        MetadataModelObject field = new MetadataModelObject();
                        field.setName(f.getName());
                        if (f.getAnnotatedType().toString().contains("io.debezium")) {
                                field.setType("class");
                        } else {
                                field.setType(f.getAnnotatedType().toString());
                        }


                        if (f.getAnnotatedType().toString().contains("io.debezium")) {
                                Class x = ClassLoader.getSystemClassLoader().loadClass(f.getType().getName());
                                if (x.isEnum()) {
                                        field.setType("enum");
                                        List<String> variants = new ArrayList<>();

                                        for (Object s : x.getEnumConstants()) {
                                                variants.add(s.toString());
                                        }
                                        field.setVariants(variants);
                                }
                                else if (x.isInterface()) {
                                        field.setType("interface");
                                        processInterfaceClass(x, field);
                                } else {
                                        printFields(x, field);
                                }
                        }
                        fields.add(field);
                }
                metadataModelObject.setFields(fields);
        }

}
