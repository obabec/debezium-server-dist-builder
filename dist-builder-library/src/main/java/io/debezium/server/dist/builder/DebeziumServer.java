package io.debezium.server.dist.builder;

import io.debezium.server.dist.builder.modules.ModuleNode;
import io.debezium.server.dist.builder.modules.SinkNode;
import io.debezium.server.dist.builder.modules.SourceNode;
import io.sundr.builder.annotations.Buildable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Buildable
public class DebeziumServer {
    private SourceNode sourceNode;
    private SinkNode sinkNode;


}
