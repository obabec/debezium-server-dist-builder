package io.debezium.server.dist.builder.modules.config.types;

public enum CaptureMode {
    CHANGE_STREAMS,
    CHANGE_STREAMS_UPDATE_FULL,
    CHANGE_STREAMS_UPDATE_FULL_WITH_PRE_IMAGE,
    CHANGE_STREAMS_WITH_PRE_IMAGE,
}
