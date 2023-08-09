package io.debezium.server.dist.builder;


import java.util.Properties;

public class ConfigurationSerializer {
    static public String configToPropertiesFile(DebeziumServer debeziumServer) {
        StringBuilder stringBuilder = new StringBuilder();
        Properties properties = debeziumServer.toProperties();

        for (Object key : properties.keySet()) {
            String k = key.toString();
            stringBuilder.append(k);
            stringBuilder.append("=");
            stringBuilder.append(properties.get(k).toString());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
