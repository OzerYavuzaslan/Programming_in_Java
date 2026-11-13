package com.omniflow.order.infra.id;

import com.github.f4b6a3.uuid.UuidCreator;
import com.omniflow.order.domain.port.out.IdGeneratorPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Infrastructure adapter producing time-ordered UUID v7 identifiers.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
@Component
public class UuidV7IdGeneratorAdapter implements IdGeneratorPort {
    @Override
    public UUID newId() {
        return UuidCreator.getTimeOrderedEpoch();
    }
}