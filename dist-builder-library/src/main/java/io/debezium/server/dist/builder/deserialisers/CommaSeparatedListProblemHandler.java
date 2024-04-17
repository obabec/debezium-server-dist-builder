/*
 * MIT License
 *
 * Copyright (c) [2024] [Ondrej Babec <ond.babec@gmail.com>]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE
 * ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.debezium.server.dist.builder.deserialisers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;

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
