package io.debezium.server.dist.builder;


import java.lang.reflect.Field;
import java.util.Properties;

//TODO: this should be custom serializer which translates Nodes configs to actual properties format for server
public class ConfigurationSerializer {
    static public String configToPropertiesFile(DebeziumServer debeziumServer) {
        Class c = debeziumServer.getClass();
        Field [] pFields = c.getDeclaredFields();
        Properties fullProperties = new Properties();

        for (Field f: pFields) {
            String attributeNameP = f.getName();
            String attributeName;
        }
        return null;
    }


    static private Properties resolveAttribute() {
        return null;
    }
}
