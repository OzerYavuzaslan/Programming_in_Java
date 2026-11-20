package com.omniflow.common.event;

/**
 * Immutable utility class holding centralized Kafka topic names
 * and event schema version constants.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public final class OmniFlowEventTopics {
    private OmniFlowEventTopics() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static final int SCHEMA_VERSION_V1 = 1;
    public static final String ORDER_EVENTS_V1 = "omniflow.order.events.v1";
}