package com.omniflow.order.infra.web.controller;

import com.omniflow.common.result.Result;
import com.omniflow.order.application.command.CancelOrderCommand;
import com.omniflow.order.application.command.CreateOrderCommand;
import com.omniflow.order.application.dto.OrderCreationResultDTO;
import com.omniflow.order.application.port.in.CancelOrderUseCase;
import com.omniflow.order.application.port.in.CreateOrderUseCase;
import com.omniflow.order.application.port.in.GetOrderQuery;
import com.omniflow.order.application.port.in.ListOrdersQuery;
import com.omniflow.order.domain.model.OrderAggregate;
import com.omniflow.order.domain.model.OrderError;
import com.omniflow.order.domain.model.OrderStatus;
import com.omniflow.order.domain.port.out.OrderSummaryDTO;
import com.omniflow.order.infra.web.dto.CancelOrderRequestDTO;
import com.omniflow.order.infra.web.dto.CreateOrderRequestDTO;
import com.omniflow.order.infra.web.dto.OrderResponseDTO;
import com.omniflow.order.infra.web.dto.OrderSummaryResponseDTO;
import com.omniflow.order.infra.web.exception.GlobalExceptionHandler;
import com.omniflow.order.infra.web.mapper.OrderWebMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Driving REST adapter exposing order lifecycle endpoints under {@code /api/v1/orders}.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final GetOrderQuery getOrderQuery;
    private final OrderWebMapper orderWebMapper;
    private final ListOrdersQuery listOrdersQuery;
    private final CreateOrderUseCase createOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final GlobalExceptionHandler globalExceptionHandler;
    private static final String BASE_PATH = "/api/v1/orders/";
    private static final String HEADER_TENANT_ID = "X-Tenant-Id";
    private static final String HEADER_IDEMPOTENCY_KEY = "Idempotency-Key";
    private static final String HEADER_IDEMPOTENT_REPLAYED = "Idempotent-Replayed";

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestHeader(HEADER_TENANT_ID) @NotBlank @Size(max = 32) String tenantId,
                                                        @RequestHeader(HEADER_IDEMPOTENCY_KEY) @NotBlank @Size(max = 64) String idempotencyKey,
                                                        @Valid @RequestBody CreateOrderRequestDTO createOrderRequestDTO) {
        CreateOrderCommand createOrderCommand = orderWebMapper.toCreateOrderCommand(tenantId, idempotencyKey, createOrderRequestDTO);
        OrderCreationResultDTO orderCreationResultDTO = createOrderUseCase.createOrder(createOrderCommand);
        OrderResponseDTO orderResponseDTO = orderWebMapper.toOrderResponseDTO(orderCreationResultDTO.orderAggregate());

        if (orderCreationResultDTO.replayed()) {
            return ResponseEntity
                    .ok()
                    .header(HEADER_IDEMPOTENT_REPLAYED, Boolean.TRUE.toString())
                    .body(orderResponseDTO);
        }

        URI locationUri = URI.create(BASE_PATH + orderResponseDTO.id());
        return ResponseEntity.created(locationUri).body(orderResponseDTO);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(@RequestHeader(HEADER_TENANT_ID) @NotBlank @Size(max = 32) String tenantId,
                                                     @PathVariable UUID orderId) {
        OrderAggregate orderAggregate = getOrderQuery.getOrder(tenantId, orderId);
        OrderResponseDTO orderResponseDTO = orderWebMapper.toOrderResponseDTO(orderAggregate);
        return ResponseEntity.ok(orderResponseDTO);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Object> cancelOrder(@RequestHeader(HEADER_TENANT_ID) @NotBlank @Size(max = 32) String tenantId,
                                              @PathVariable UUID orderId,
                                              @Valid @RequestBody CancelOrderRequestDTO cancelOrderRequestDTO) {
        CancelOrderCommand cancelOrderCommand = orderWebMapper.toCancelOrderCommand(tenantId, orderId, cancelOrderRequestDTO);
        Result<OrderAggregate, OrderError> result = cancelOrderUseCase.cancelOrder(cancelOrderCommand);

        return switch (result) {
            case Result.Success<OrderAggregate, OrderError> success -> {
                OrderResponseDTO orderResponseDTO = orderWebMapper.toOrderResponseDTO(success.value());
                yield ResponseEntity.ok(orderResponseDTO);
            }

            case Result.Failure<OrderAggregate, OrderError> failure -> {
                ProblemDetail problemDetail = globalExceptionHandler.toProblemDetail(failure.error());
                yield ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
            }
        };
    }

    @GetMapping
    public ResponseEntity<List<OrderSummaryResponseDTO>> listOrders(@RequestHeader(HEADER_TENANT_ID) @NotBlank @Size(max = 32) String tenantId,
                                                                    @RequestParam @NotBlank String status,
                                                                    @RequestParam(required = false) Instant createdBefore,
                                                                    @RequestParam(defaultValue = "50") int limit) {
        OrderStatus orderStatus = orderWebMapper.toOrderStatus(status);
        List<OrderSummaryDTO> orderSummaryDTOList = listOrdersQuery.listOrders(tenantId, orderStatus, createdBefore, limit);
        List<OrderSummaryResponseDTO> orderSummaryResponseDTOList = orderWebMapper.toOrderSummaryResponseDTOList(orderSummaryDTOList);
        return ResponseEntity.ok(orderSummaryResponseDTOList);
    }
}