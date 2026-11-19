package com.omniflow.order.infra.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Web request DTO carrying the business reason for cancelling an order.
 *
 * @param reason business justification of the cancellation (max 64 chars)
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record CancelOrderRequestDTO(
        @NotBlank @Size(max = 64) String reason
) {
}