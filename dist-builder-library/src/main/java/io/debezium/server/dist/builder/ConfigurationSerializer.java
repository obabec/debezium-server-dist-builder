package io.debezium.server.dist.builder;


import java.util.Properties;

/**
 * ConfigurationSerializer serializes the properties to Java properties style key=value.
 */
public class ConfigurationSerializer {
    /**
     * Serializes whole CustomDebeziumServer configuration to Java properties style key=value.
     * @param customDebeziumServer Custom Debezium Server object
     * @return Serialized String
     */
    static public String configToPropertiesFile(CustomDebeziumServer customDebeziumServer) {
        StringBuilder stringBuilder = new StringBuilder();
        Properties properties = customDebeziumServer.toProperties();

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
