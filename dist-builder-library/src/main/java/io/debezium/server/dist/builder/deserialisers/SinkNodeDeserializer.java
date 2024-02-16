package io.debezium.server.dist.builder.deserialisers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.sink.EventHubs;
import io.debezium.server.dist.builder.modules.sink.Http;
import io.debezium.server.dist.builder.modules.sink.Infinispan;
import io.debezium.server.dist.builder.modules.sink.Kafka;
import io.debezium.server.dist.builder.modules.sink.Kinesis;
import io.debezium.server.dist.builder.modules.sink.NatsJetSteaming;
import io.debezium.server.dist.builder.modules.sink.NatsSteaming;
import io.debezium.server.dist.builder.modules.sink.Pravega;
import io.debezium.server.dist.builder.modules.sink.PubSub;
import io.debezium.server.dist.builder.modules.sink.PubSubLite;
import io.debezium.server.dist.builder.modules.sink.Pulsar;
import io.debezium.server.dist.builder.modules.sink.RabbitMQ;
import io.debezium.server.dist.builder.modules.sink.Redis;
import io.debezium.server.dist.builder.modules.sink.RocketMQ;
import io.debezium.server.dist.builder.utils.DeserializationUtils;

import java.io.IOException;

public class SinkNodeDeserializer extends StdDeserializer<SinkNode> {

    public SinkNodeDeserializer() {
        this(null);
    }

    protected SinkNodeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SinkNode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.readValueAsTree();
        if (node.fields().hasNext()) {
            String key = node.fields().next().getKey();
            ObjectMapper mapper = DeserializationUtils.getCustomDeserializationMapper();

            switch (key) {
                case "EvenHubs":
                    return mapper.readValue(node.fields().next().getValue().toString(), EventHubs.class);
                case "Http":
                    return mapper.readValue(node.fields().next().getValue().toString(), Http.class);
                case "Infinispan":
                    return mapper.readValue(node.fields().next().getValue().toString(), Infinispan.class);
                case "Kafka":
                    return mapper.readValue(node.fields().next().getValue().toString(), Kafka.class);
                case "Kinesis":
                    return mapper.readValue(node.fields().next().getValue().toString(), Kinesis.class);
                case "NatsJetSteaming":
                    return mapper.readValue(node.fields().next().getValue().toString(), NatsJetSteaming.class);
                case "NatsSteaming":
                    return mapper.readValue(node.fields().next().getValue().toString(), NatsSteaming.class);
                case "Pravega":
                    return mapper.readValue(node.fields().next().getValue().toString(), Pravega.class);
                case "PubSub":
                    return mapper.readValue(node.fields().next().getValue().toString(), PubSub.class);
                case "PubSubLite":
                    return mapper.readValue(node.fields().next().getValue().toString(), PubSubLite.class);
                case "Pulsar":
                    return mapper.readValue(node.fields().next().getValue().toString(), Pulsar.class);
                case "RabbitMQ":
                    return mapper.readValue(node.fields().next().getValue().toString(), RabbitMQ.class);
                case "Redis":
                    return mapper.readValue(node.fields().next().getValue().toString(), Redis.class);
                default:
                    return mapper.readValue(node.fields().next().getValue().toString(), RocketMQ.class);
            }
        } else {
            throw new IOException("No Sink node specified");
        }
    }
}
