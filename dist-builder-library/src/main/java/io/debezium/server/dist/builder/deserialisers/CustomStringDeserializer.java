package io.debezium.server.dist.builder.deserialisers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

import java.io.IOException;

public class CustomStringDeserializer extends StdDeserializer<String> {
    public CustomStringDeserializer() {
        this(null);
    }

    protected CustomStringDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String result = StringDeserializer.instance.deserialize(jsonParser, deserializationContext);
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }
}
