package io.debezium.server.dist.builder.deserialisers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.source.storage.SchemaHistoryStorage;
import io.debezium.server.dist.builder.modules.source.storage.StorageConfig;

public class DeserializationUtils {
    public static ObjectMapper getCustomMapper() {
        return JsonMapper.builder().addHandler(new CommaSeparatedListProblemHandler())
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .serializationInclusion(JsonInclude.Include.NON_EMPTY)
                .addModule(new SimpleModule().addDeserializer(String.class, new CustomStringDeserializer()))
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                .build();
    }

    public static ObjectMapper getDefaultMapper() {
        SimpleModule sourceDes = new SimpleModule();
        sourceDes.addDeserializer(SourceNode.class, new SourceNodeDeserializer());
        sourceDes.addDeserializer(SinkNode.class, new SinkNodeDeserializer());
        sourceDes.addDeserializer(SchemaHistoryStorage.class, new SchemaHistoryStorageDeserializer());
        sourceDes.addDeserializer(StorageConfig.class, new StorageConfigDeserializer());
        sourceDes.addDeserializer(String.class, new CustomStringDeserializer());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(sourceDes);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        return objectMapper;
    }
}
