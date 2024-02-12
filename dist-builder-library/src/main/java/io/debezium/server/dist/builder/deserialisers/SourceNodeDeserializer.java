package io.debezium.server.dist.builder.deserialisers;

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

import java.io.IOException;

public class SourceNodeDeserializer extends StdDeserializer<SourceNode> {

    public SourceNodeDeserializer() {
        this(null);
    }

    public SourceNodeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SourceNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.readValueAsTree();
        if (node.fields().hasNext()) {
            String key = node.fields().next().getKey();
            ObjectMapper mapper = DeserializationUtils.getCustomMapper();

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
