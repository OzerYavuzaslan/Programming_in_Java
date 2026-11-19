package com.omniflow.order.infra.web.mapper;

import com.omniflow.order.application.command.CancelOrderCommand;
import com.omniflow.order.application.command.CreateOrderCommand;
import com.omniflow.order.application.command.CreateOrderItemCommand;
import com.omniflow.order.domain.model.OrderAggregate;
import com.omniflow.order.domain.model.OrderItem;
import com.omniflow.order.domain.model.OrderStatus;
import com.omniflow.order.domain.port.out.OrderSummaryDTO;
import com.omniflow.order.infra.persistence.mapper.OrderStatusCodec;
import com.omniflow.order.infra.web.dto.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import static com.omniflow.order.domain.constant.OrderErrorMessages.*;

/**
 * Web-layer mapper converting between HTTP DTOs and application/domain models.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
@Component
public class OrderWebMapper {
    public CreateOrderCommand toCreateOrderCommand(String tenantId, String idempotencyKey, CreateOrderRequestDTO createOrderRequestDTO) {
        Objects.requireNonNull(createOrderRequestDTO, MSG_CREATE_COMMAND_NULL);

        List<CreateOrderItemCommand> createOrderItemCommandList = createOrderRequestDTO.orderItemRequestDTOList()
                .stream()
                .map(orderItemRequestDTO -> new CreateOrderItemCommand(
                        orderItemRequestDTO.productId(),
                        orderItemRequestDTO.productName(),
                        orderItemRequestDTO.quantity(),
                        orderItemRequestDTO.unitPrice(),
                        orderItemRequestDTO.currency()
                )).toList();

        return new CreateOrderCommand(tenantId,
                createOrderRequestDTO.customerId(),
                idempotencyKey,
                createOrderItemCommandList
        );
    }

    public CancelOrderCommand toCancelOrderCommand(String tenantId, UUID orderId, CancelOrderRequestDTO cancelOrderRequestDTO) {
        Objects.requireNonNull(cancelOrderRequestDTO, MSG_CANCEL_COMMAND_NULL);
        return new CancelOrderCommand(tenantId, orderId, cancelOrderRequestDTO.reason());
    }

    public OrderResponseDTO toOrderResponseDTO(OrderAggregate orderAggregate) {
        Objects.requireNonNull(orderAggregate, MSG_ORDER_NULL);

        List<OrderItemResponseDTO> orderItemResponseDTOList = Collections.emptyList();

        if (CollectionUtils.isNotEmpty(orderAggregate.orderItemList())) {
            orderItemResponseDTOList = orderAggregate.orderItemList()
                    .stream()
                    .map(this::toOrderItemResponseDTO)
                    .toList();
        }

        return new OrderResponseDTO(orderAggregate.id(),
                orderAggregate.tenantId(),
                orderAggregate.customerId(),
                OrderStatusCodec.toCode(orderAggregate.orderStatus()),
                orderAggregate.totalAmount().amount(),
                orderAggregate.totalAmount().currency(),
                orderItemResponseDTOList
        );
    }

    public List<OrderSummaryResponseDTO> toOrderSummaryResponseDTOList(List<OrderSummaryDTO> orderSummaryDTOList) {
        if (CollectionUtils.isEmpty(orderSummaryDTOList)) {
            return Collections.emptyList();
        }

        return orderSummaryDTOList.stream()
                .map(orderSummaryDTO -> new OrderSummaryResponseDTO(
                        orderSummaryDTO.id(),
                        orderSummaryDTO.totalAmount().amount(),
                        orderSummaryDTO.totalAmount().currency(),
                        orderSummaryDTO.createdAt()
                )).toList();
    }

    public OrderStatus toOrderStatus(String statusCode) {
        if (Objects.isNull(statusCode) || statusCode.isBlank()) {
            throw new IllegalArgumentException(MSG_STATUS_CODE_BLANK);
        }

        String normalizedStatusCode = statusCode.trim().toUpperCase(Locale.ROOT);

        return switch (normalizedStatusCode) {
            case OrderStatusCodec.CODE_CREATED -> new OrderStatus.Created();
            case OrderStatusCodec.CODE_PAYMENT_PENDING -> new OrderStatus.PaymentPending();
            case OrderStatusCodec.CODE_PAID -> new OrderStatus.Paid();
            case OrderStatusCodec.CODE_CANCELLED -> new OrderStatus.Cancelled();
            default -> throw new IllegalArgumentException(MSG_UNKNOWN_STATUS_CODE + statusCode);
        };
    }

    private OrderItemResponseDTO toOrderItemResponseDTO(OrderItem orderItem) {
        return new OrderItemResponseDTO(
                orderItem.id(),
                orderItem.productId(),
                orderItem.productName(),
                orderItem.quantity(),
                orderItem.unitPrice().amount(),
                orderItem.lineTotal().amount(),
                orderItem.unitPrice().currency()
        );
    }
}