package io.debezium.server.dist.builder.modules.config.types;

import java.util.List;

public class SignalConfiguration {
    private final String PREFIX = "signal.kafka";

    protected String topic;

    protected List<String> bootstrapServers;

    protected Integer pollTimeoutMs;

}
