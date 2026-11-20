package com.omniflow.order.infra.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omniflow.common.result.Result;
import com.omniflow.order.application.command.CancelOrderCommand;
import com.omniflow.order.application.command.CreateOrderCommand;
import com.omniflow.order.application.dto.OrderCreationResultDTO;
import com.omniflow.order.application.exception.OrderNotFoundException;
import com.omniflow.order.application.port.in.CancelOrderUseCase;
import com.omniflow.order.application.port.in.CreateOrderUseCase;
import com.omniflow.order.application.port.in.GetOrderQuery;
import com.omniflow.order.application.port.in.ListOrdersQuery;
import com.omniflow.order.domain.model.*;
import com.omniflow.order.domain.port.out.OrderSummaryDTO;
import com.omniflow.order.infra.persistence.mapper.OrderStatusCodec;
import com.omniflow.order.infra.web.dto.CancelOrderRequestDTO;
import com.omniflow.order.infra.web.dto.CreateOrderRequestDTO;
import com.omniflow.order.infra.web.dto.OrderItemRequestDTO;
import com.omniflow.order.infra.web.exception.GlobalExceptionHandler;
import com.omniflow.order.infra.web.mapper.OrderWebMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Web slice tests for {@link OrderController} asserting REST contracts, HTTP statuses, and I18N.
 *
 * @author Özer Yavuzaslan
 * @version 1.0.0
 */
@WebMvcTest(OrderController.class)
@Import({GlobalExceptionHandler.class, OrderWebMapper.class})
class OrderControllerTest {
    private static final String BASE_URL = "/api/v1/orders";
    private static final String HEADER_TENANT_ID = "X-Tenant-Id";
    private static final String HEADER_IDEMPOTENCY_KEY = "Idempotency-Key";
    private static final String HEADER_IDEMPOTENT_REPLAYED = "Idempotent-Replayed";
    private static final String TENANT_ID = "tenant-001";
    private static final String IDEMPOTENCY_KEY = "idemp-key-12345";
    private static final String CURRENCY_TRY = "TRY";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateOrderUseCase createOrderUseCase;

    @MockBean
    private CancelOrderUseCase cancelOrderUseCase;

    @MockBean
    private GetOrderQuery getOrderQuery;

    @MockBean
    private ListOrdersQuery listOrdersQuery;

    private UUID sampleOrderId;
    private UUID sampleCustomerId;
    private OrderAggregate sampleOrderAggregate;
    private CreateOrderRequestDTO validCreateOrderRequestDTO;

    @BeforeEach
    void setUp() {
        sampleOrderId = UUID.randomUUID();
        sampleCustomerId = UUID.randomUUID();

        OrderItem sampleOrderItem = new OrderItem(
                UUID.randomUUID(),
                "PROD-001",
                "Ultra Smart Watch",
                2,
                new Money(new BigDecimal("150.00"), CURRENCY_TRY)
        );

        sampleOrderAggregate = new OrderAggregate(
                sampleOrderId,
                TENANT_ID,
                sampleCustomerId,
                IDEMPOTENCY_KEY,
                List.of(sampleOrderItem),
                new OrderStatus.Created(),
                new Money(new BigDecimal("300.00"), CURRENCY_TRY),
                List.of()
        );

        OrderItemRequestDTO sampleOrderItemRequestDTO = new OrderItemRequestDTO(
                "PROD-001",
                "Ultra Smart Watch",
                2,
                new BigDecimal("150.00"),
                CURRENCY_TRY
        );

        validCreateOrderRequestDTO = new CreateOrderRequestDTO(
                sampleCustomerId,
                List.of(sampleOrderItemRequestDTO)
        );
    }

    @Test
    @DisplayName("POST /api/v1/orders should return 201 Created with Location header when new order created")
    void createOrder_shouldReturn201Created_withLocationHeader_whenNewOrderCreated() throws Exception {
        OrderCreationResultDTO orderCreationResultDTO = new OrderCreationResultDTO(sampleOrderAggregate, false);
        when(createOrderUseCase.createOrder(any(CreateOrderCommand.class))).thenReturn(orderCreationResultDTO);

        mockMvc.perform(post(BASE_URL)
                        .header(HEADER_TENANT_ID, TENANT_ID)
                        .header(HEADER_IDEMPOTENCY_KEY, IDEMPOTENCY_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCreateOrderRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", BASE_URL + "/" + sampleOrderId))
                .andExpect(header().doesNotExist(HEADER_IDEMPOTENT_REPLAYED))
                .andExpect(jsonPath("$.id").value(sampleOrderId.toString()))
                .andExpect(jsonPath("$.tenantId").value(TENANT_ID))
                .andExpect(jsonPath("$.customerId").value(sampleCustomerId.toString()))
                .andExpect(jsonPath("$.status").value(OrderStatusCodec.CODE_CREATED))
                .andExpect(jsonPath("$.totalAmount").value(300.00))
                .andExpect(jsonPath("$.currency").value("TRY"))
                .andExpect(jsonPath("$.orderItemResponseDTOList", hasSize(1)))
                .andExpect(jsonPath("$.orderItemResponseDTOList[0].productId").value("PROD-001"));
    }

    @Test
    @DisplayName("POST /api/v1/orders should return 200 OK with Idempotent-Replayed header when replay detected")
    void createOrder_shouldReturn200Ok_withIdempotentReplayedHeader_whenReplayed() throws Exception {
        OrderCreationResultDTO orderCreationResultDTO = new OrderCreationResultDTO(sampleOrderAggregate, true);
        when(createOrderUseCase.createOrder(any(CreateOrderCommand.class))).thenReturn(orderCreationResultDTO);

        mockMvc.perform(post(BASE_URL)
                        .header(HEADER_TENANT_ID, TENANT_ID)
                        .header(HEADER_IDEMPOTENCY_KEY, IDEMPOTENCY_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCreateOrderRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(header().string(HEADER_IDEMPOTENT_REPLAYED, "true"))
                .andExpect(jsonPath("$.id").value(sampleOrderId.toString()))
                .andExpect(jsonPath("$.status").value(OrderStatusCodec.CODE_CREATED));
    }

    @Test
    @DisplayName("POST /api/v1/orders should return 400 Bad Request ProblemDetail when X-Tenant-Id header is missing")
    void createOrder_shouldReturn400BadRequest_whenTenantIdHeaderMissing() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .header(HEADER_IDEMPOTENCY_KEY, IDEMPOTENCY_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCreateOrderRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("/problems/missing-header"))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @DisplayName("POST /api/v1/orders should return 400 Bad Request ProblemDetail when body is invalid")
    void createOrder_shouldReturn400BadRequest_whenRequestBodyInvalid() throws Exception {
        CreateOrderRequestDTO invalidCreateOrderRequestDTO = new CreateOrderRequestDTO(null, List.of());

        mockMvc.perform(post(BASE_URL)
                        .header(HEADER_TENANT_ID, TENANT_ID)
                        .header(HEADER_IDEMPOTENCY_KEY, IDEMPOTENCY_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCreateOrderRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("/problems/validation-error"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors", notNullValue()));
    }

    @Test
    @DisplayName("GET /api/v1/orders/{orderId} should return 200 OK when order exists")
    void getOrder_shouldReturn200Ok_whenOrderExists() throws Exception {
        when(getOrderQuery.getOrder(TENANT_ID, sampleOrderId)).thenReturn(sampleOrderAggregate);

        mockMvc.perform(get(BASE_URL + "/" + sampleOrderId)
                        .header(HEADER_TENANT_ID, TENANT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleOrderId.toString()))
                .andExpect(jsonPath("$.tenantId").value(TENANT_ID))
                .andExpect(jsonPath("$.status").value(OrderStatusCodec.CODE_CREATED))
                .andExpect(jsonPath("$.totalAmount").value(300.00));
    }

    @Test
    @DisplayName("GET /api/v1/orders/{orderId} should return 404 Not Found ProblemDetail when order not found")
    void getOrder_shouldReturn404NotFound_whenOrderNotFound() throws Exception {
        when(getOrderQuery.getOrder(TENANT_ID, sampleOrderId))
                .thenThrow(new OrderNotFoundException(TENANT_ID, sampleOrderId));

        mockMvc.perform(get(BASE_URL + "/" + sampleOrderId)
                        .header(HEADER_TENANT_ID, TENANT_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("/problems/order-not-found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.orderId").value(sampleOrderId.toString()))
                .andExpect(jsonPath("$.tenantId").value(TENANT_ID));
    }

    @Test
    @DisplayName("POST /api/v1/orders/{orderId}/cancel should return 200 OK when cancellation succeeds")
    void cancelOrder_shouldReturn200Ok_whenCancellationSucceeds() throws Exception {
        CancelOrderRequestDTO cancelOrderRequestDTO = new CancelOrderRequestDTO("Customer requested refund");
        OrderAggregate cancelledOrderAggregate = new OrderAggregate(
                sampleOrderId,
                TENANT_ID,
                sampleCustomerId,
                IDEMPOTENCY_KEY,
                sampleOrderAggregate.orderItemList(),
                new OrderStatus.Cancelled(),
                sampleOrderAggregate.totalAmount(),
                List.of()
        );

        when(cancelOrderUseCase.cancelOrder(any(CancelOrderCommand.class)))
                .thenReturn(Result.success(cancelledOrderAggregate));

        mockMvc.perform(post(BASE_URL + "/" + sampleOrderId + "/cancel")
                        .header(HEADER_TENANT_ID, TENANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cancelOrderRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleOrderId.toString()))
                .andExpect(jsonPath("$.status").value(OrderStatusCodec.CODE_CANCELLED));
    }

    @Test
    @DisplayName("POST /api/v1/orders/{orderId}/cancel should return 409 Conflict ProblemDetail on invalid state transition")
    void cancelOrder_shouldReturn409Conflict_whenInvalidStateTransition() throws Exception {
        CancelOrderRequestDTO cancelOrderRequestDTO = new CancelOrderRequestDTO("Customer requested refund");
        OrderError.InvalidStateTransition invalidStateTransition = new OrderError.InvalidStateTransition(
                new OrderStatus.Paid(),
                new OrderStatus.Cancelled(),
                "Cannot cancel a paid order"
        );

        when(cancelOrderUseCase.cancelOrder(any(CancelOrderCommand.class)))
                .thenReturn(Result.failure(invalidStateTransition));

        mockMvc.perform(post(BASE_URL + "/" + sampleOrderId + "/cancel")
                        .header(HEADER_TENANT_ID, TENANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cancelOrderRequestDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.type").value("/problems/invalid-state-transition"))
                .andExpect(jsonPath("$.status").value(409));
    }

    @Test
    @DisplayName("POST /api/v1/orders/{orderId}/cancel should return 422 Unprocessable Entity ProblemDetail on invalid cancel reason")
    void cancelOrder_shouldReturn422UnprocessableEntity_whenInvalidCancelReason() throws Exception {
        CancelOrderRequestDTO cancelOrderRequestDTO = new CancelOrderRequestDTO("Valid reason for request");
        OrderError.InvalidCancellationReason invalidCancellationReason =
                new OrderError.InvalidCancellationReason("Cancellation reason must be between 5 and 500 characters");

        when(cancelOrderUseCase.cancelOrder(any(CancelOrderCommand.class)))
                .thenReturn(Result.failure(invalidCancellationReason));

        mockMvc.perform(post(BASE_URL + "/" + sampleOrderId + "/cancel")
                        .header(HEADER_TENANT_ID, TENANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cancelOrderRequestDTO)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value("/problems/invalid-cancellation-reason"))
                .andExpect(jsonPath("$.status").value(422));
    }

    @Test
    @DisplayName("GET /api/v1/orders should return 200 OK with summary list for keyset pagination")
    void listOrders_shouldReturn200Ok_withSummaryList() throws Exception {
        OrderSummaryDTO orderSummaryDTO = new OrderSummaryDTO(
                sampleOrderId,
                new Money(new BigDecimal("300.00"), CURRENCY_TRY),
                Instant.parse("2026-03-30T10:15:30Z")
        );

        when(listOrdersQuery.listOrders(eq(TENANT_ID), any(OrderStatus.Created.class), isNull(), eq(20)))
                .thenReturn(List.of(orderSummaryDTO));

        mockMvc.perform(get(BASE_URL)
                        .header(HEADER_TENANT_ID, TENANT_ID)
                        .param("status", "CREATED")
                        .param("limit", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(sampleOrderId.toString()))
                .andExpect(jsonPath("$[0].totalAmount").value(300.00))
                .andExpect(jsonPath("$[0].currency").value("TRY"))
                .andExpect(jsonPath("$[0].createdAt").value("2026-03-30T10:15:30Z"));
    }

    @Test
    @DisplayName("GET /api/v1/orders should return 400 Bad Request ProblemDetail when status parameter is invalid")
    void listOrders_shouldReturn400BadRequest_whenStatusIsInvalid() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .header(HEADER_TENANT_ID, TENANT_ID)
                        .param("status", "UNKNOWN_STATUS")
                        .param("limit", "20"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("/problems/invalid-request"))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @DisplayName("I18N: Should return Turkish localized messages when Accept-Language is tr")
    void i18n_shouldReturnTurkishMessages_whenAcceptLanguageIsTr() throws Exception {
        when(getOrderQuery.getOrder(TENANT_ID, sampleOrderId))
                .thenThrow(new OrderNotFoundException(TENANT_ID, sampleOrderId));

        mockMvc.perform(get(BASE_URL + "/" + sampleOrderId)
                        .header(HEADER_TENANT_ID, TENANT_ID)
                        .header("Accept-Language", "tr"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Sipariş Bulunamadı"))
                .andExpect(jsonPath("$.detail", containsString("sipariş bulunamadı")));
    }

    @Test
    @DisplayName("I18N: Should return Dutch localized messages when Accept-Language is nl")
    void i18n_shouldReturnDutchMessages_whenAcceptLanguageIsNl() throws Exception {
        when(getOrderQuery.getOrder(TENANT_ID, sampleOrderId))
                .thenThrow(new OrderNotFoundException(TENANT_ID, sampleOrderId));

        mockMvc.perform(get(BASE_URL + "/" + sampleOrderId)
                        .header(HEADER_TENANT_ID, TENANT_ID)
                        .header("Accept-Language", "nl"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Bestelling Niet Gevonden"))
                .andExpect(jsonPath("$.detail", containsString("Bestelling")));
    }

    @Test
    @DisplayName("I18N: Should return English localized messages when Accept-Language is en")
    void i18n_shouldReturnEnglishMessages_whenAcceptLanguageIsEn() throws Exception {
        when(getOrderQuery.getOrder(TENANT_ID, sampleOrderId))
                .thenThrow(new OrderNotFoundException(TENANT_ID, sampleOrderId));

        mockMvc.perform(get(BASE_URL + "/" + sampleOrderId)
                        .header(HEADER_TENANT_ID, TENANT_ID)
                        .header("Accept-Language", "en"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Order Not Found"))
                .andExpect(jsonPath("$.detail", containsString("was not found")));
    }
}
