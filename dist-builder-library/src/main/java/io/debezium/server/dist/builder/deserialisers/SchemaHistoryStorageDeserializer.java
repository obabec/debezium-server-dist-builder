package io.debezium.server.dist.builder.deserialisers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.debezium.server.dist.builder.modules.source.storage.AzureBlobStorageConfig;
import io.debezium.server.dist.builder.modules.source.storage.FileStorage;
import io.debezium.server.dist.builder.modules.source.storage.KafkaStorageConfig;
import io.debezium.server.dist.builder.modules.source.storage.RedisStorageConfig;
import io.debezium.server.dist.builder.modules.source.storage.RocketMQStorageConfig;
import io.debezium.server.dist.builder.modules.source.storage.S3StorageConfig;
import io.debezium.server.dist.builder.modules.source.storage.SchemaHistoryStorage;
import io.debezium.server.dist.builder.utils.DeserializationUtils;

import java.io.IOException;

public class SchemaHistoryStorageDeserializer extends StdDeserializer<SchemaHistoryStorage> {

    public SchemaHistoryStorageDeserializer() {
        this(null);
    }

    public SchemaHistoryStorageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SchemaHistoryStorage deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.readValueAsTree();
        if (node.fields().hasNext()) {
            String key = node.fields().next().getKey();
            ObjectMapper mapper = DeserializationUtils.getCustomDeserializationMapper();

            switch (key) {
                case "FileStorage":
                    return mapper.readValue(node.fields().next().getValue().toString(), FileStorage.class);
                case "RedisStorageConfig":
                    return mapper.readValue(node.fields().next().getValue().toString(), RedisStorageConfig.class);
                case "S3StorageConfig":
                    return mapper.readValue(node.fields().next().getValue().toString(), S3StorageConfig.class);
                case "KafkaStorageConfig":
                    return mapper.readValue(node.fields().next().getValue().toString(), KafkaStorageConfig.class);
                case "AzureBlobStorageConfig":
                    return mapper.readValue(node.fields().next().getValue().toString(), AzureBlobStorageConfig.class);
                default:
                    return mapper.readValue(node.fields().next().getValue().toString(), RocketMQStorageConfig.class);
            }
        } else {
            throw new IOException("No Schema History Storage specified");
        }
    }
}
