package io.debezium.server.dist.builder.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommaSeparatedListProblemHandler extends DeserializationProblemHandler {
    @Override
    public Object handleMissingInstantiator(DeserializationContext ctxt, Class<?> instClass, ValueInstantiator valueInsta, JsonParser p, String msg) throws IOException {
        if (instClass.getName().equals("java.util.ArrayList")) {
            return deserializeSimpleCommaSeparatedList(p);
        }
        return super.handleMissingInstantiator(ctxt, instClass, valueInsta, p, msg);
    }

    @Override
    public Object handleUnexpectedToken(DeserializationContext ctxt, JavaType targetType, JsonToken t, JsonParser p, String failureMsg) throws IOException {
        if (t == JsonToken.VALUE_STRING && targetType.isCollectionLikeType()) {
            return deserializeCommaSeparatedList(targetType, p);
        }
        return super.handleUnexpectedToken(ctxt, targetType, t, p, failureMsg);
    }

    private Object deserializeSimpleCommaSeparatedList(JsonParser parser) throws IOException {
        String[] vals = parser.getText().split(",");
        return new ArrayList<>(Arrays.asList(vals));
    }


    private Object deserializeCommaSeparatedList(JavaType listType, JsonParser parser) throws IOException {
        String[] vals = parser.getText().split(",");

        ObjectMapper mapper = (ObjectMapper) parser.getCodec();
        JavaType javaType = listType.getContentType();

        List<Object> result = new ArrayList<>();
        for (String value : vals) {
            result.add(convertToType(mapper, javaType, value));
        }
        return result;
    }

    private Object convertToType(ObjectMapper mapper, JavaType javaType, String value) throws JsonProcessingException {
        final String json = "\"" + value.trim() + "\"";
        return mapper.readValue(json, javaType);
    }
}
