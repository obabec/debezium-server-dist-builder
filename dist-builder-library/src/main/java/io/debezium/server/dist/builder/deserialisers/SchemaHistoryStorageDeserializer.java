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

public class SchemaHistoryStorageDeserializer extends StdDeserializer<SchemaHistoryStorage> {

    public SchemaHistoryStorageDeserializer() {
        this(null);
    }

    public SchemaHistoryStorageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SchemaHistoryStorage deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
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
