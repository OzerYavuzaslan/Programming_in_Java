package com.omniflow.order.infra.web.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Web response DTO representing the full state of an order.
 *
 * @param id                       unique order identifier
 * @param tenantId                 multi-tenant partition identifier
 * @param customerId               ordering customer identifier
 * @param status                   current lifecycle status code
 * @param totalAmount              total monetary amount of the order
 * @param currency                 ISO-4217 currency code
 * @param orderItemResponseDTOList list of order line items
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
public record OrderResponseDTO(
        UUID id,
        String tenantId,
        UUID customerId,
        String status,
        BigDecimal totalAmount,
        String currency,
        List<OrderItemResponseDTO> orderItemResponseDTOList
) {
}