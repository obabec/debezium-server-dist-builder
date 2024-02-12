package io.debezium.server.dist.builder.deserialisers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.debezium.server.dist.builder.modules.source.storage.FileStorage;
import io.debezium.server.dist.builder.modules.source.storage.RedisStorageConfig;
import io.debezium.server.dist.builder.modules.source.storage.StorageConfig;

import java.io.IOException;

public class StorageConfigDeserializer extends StdDeserializer<StorageConfig> {

    public StorageConfigDeserializer() {
        this(null);
    }
    protected StorageConfigDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public StorageConfig deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.readValueAsTree();
        if (node.fields().hasNext()) {
            String key = node.fields().next().getKey();
            ObjectMapper mapper = DeserializationUtils.getCustomMapper();

            if (key.equals("FileStorage")) {
                return mapper.readValue(node.fields().next().getValue().toString(), FileStorage.class);
            }
            return mapper.readValue(node.fields().next().getValue().toString(), RedisStorageConfig.class);
        } else {
            throw new IOException("No Storage config specified");
        }
    }
}
