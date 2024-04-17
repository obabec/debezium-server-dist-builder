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
import io.debezium.server.dist.builder.modules.SourceNode;
import io.debezium.server.dist.builder.modules.source.Db2;
import io.debezium.server.dist.builder.modules.source.Mongo;
import io.debezium.server.dist.builder.modules.source.Mysql;
import io.debezium.server.dist.builder.modules.source.Oracle;
import io.debezium.server.dist.builder.modules.source.Postgres;
import io.debezium.server.dist.builder.modules.source.SqlServer;
import io.debezium.server.dist.builder.utils.DeserializationUtils;

public class SourceNodeDeserializer extends StdDeserializer<SourceNode> {

    public SourceNodeDeserializer() {
        this(null);
    }

    public SourceNodeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SourceNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();
        if (node.fields().hasNext()) {
            String key = node.fields().next().getKey();
            ObjectMapper mapper = DeserializationUtils.getCustomDeserializationMapper();

            switch (key) {
                case "Postgres":
                    return mapper.readValue(node.fields().next().getValue().toString(), Postgres.class);
                case "SqlServer":
                    return mapper.readValue(node.fields().next().getValue().toString(), SqlServer.class);
                case "Mongo":
                    return mapper.readValue(node.fields().next().getValue().toString(), Mongo.class);
                case "Oracle":
                    return mapper.readValue(node.fields().next().getValue().toString(), Oracle.class);
                case "Db2":
                    return mapper.readValue(node.fields().next().getValue().toString(), Db2.class);
                default:
                    return mapper.readValue(node.fields().next().getValue().toString(), Mysql.class);
            }
        } else {
            throw new IOException("No Source node specified");
        }
    }
}
