package com.omniflow.order.domain.port.out;

import java.util.UUID;

/**
 * Outbound port for generating domain entity identifiers.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public interface IdGeneratorPort {
    UUID newId();
}