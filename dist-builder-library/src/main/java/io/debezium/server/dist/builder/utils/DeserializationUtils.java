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

package io.debezium.server.dist.builder.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.debezium.server.dist.builder.deserialisers.CommaSeparatedListProblemHandler;
import io.debezium.server.dist.builder.deserialisers.CustomStringDeserializer;
import io.debezium.server.dist.builder.deserialisers.SchemaHistoryStorageDeserializer;
import io.debezium.server.dist.builder.deserialisers.SinkNodeDeserializer;
import io.debezium.server.dist.builder.deserialisers.SourceNodeDeserializer;
import io.debezium.server.dist.builder.deserialisers.StorageConfigDeserializer;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.source.storage.SchemaHistoryStorage;
import io.debezium.server.dist.builder.modules.source.storage.StorageConfig;

public class DeserializationUtils {
    public static ObjectMapper getCustomDeserializationMapper() {
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
