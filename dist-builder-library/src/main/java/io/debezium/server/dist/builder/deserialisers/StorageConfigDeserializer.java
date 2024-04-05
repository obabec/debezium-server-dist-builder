package io.debezium.server.dist.builder.deserialisers;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.debezium.server.dist.builder.modules.source.storage.AzureBlobStorageConfig;
import io.debezium.server.dist.builder.modules.source.storage.FileStorage;
import io.debezium.server.dist.builder.modules.source.storage.RedisStorageConfig;
import io.debezium.server.dist.builder.modules.source.storage.StorageConfig;
import io.debezium.server.dist.builder.utils.DeserializationUtils;

public class StorageConfigDeserializer extends StdDeserializer<StorageConfig> {

    public StorageConfigDeserializer() {
        this(null);
    }

    protected StorageConfigDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public StorageConfig deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();
        if (node.fields().hasNext()) {
            String key = node.fields().next().getKey();
            ObjectMapper mapper = DeserializationUtils.getCustomDeserializationMapper();

            switch (key) {
                case "AzureBlobStorageConfig":
                    return mapper.readValue(node.fields().next().getValue().toString(), AzureBlobStorageConfig.class);
                case "FileStorage":
                    return mapper.readValue(node.fields().next().getValue().toString(), FileStorage.class);
                default:
                    return mapper.readValue(node.fields().next().getValue().toString(), RedisStorageConfig.class);
            }
        } else {
            throw new IOException("No Storage config specified");
        }
    }
}
