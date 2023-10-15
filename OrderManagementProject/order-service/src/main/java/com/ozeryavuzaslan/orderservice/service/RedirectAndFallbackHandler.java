package com.ozeryavuzaslan.orderservice.service;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaymentRequestDTOForPaymentService;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import feign.Response;

import java.util.List;

public interface RedirectAndFallbackHandler {
    Response redirectGetSpecificTaxRate();
    Response redirectReserveStocks(List<ReservedStockDTO> reservedStockDTOList);
    Response redirectMakePayment(OrderDTO orderDTO, PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService);
    Response redirectDecreaseStocks(List<ReservedStockDTO> reservedStockDTOList);
    Response redirectRollbackReservedStocks(List<ReservedStockDTO> reservedStockDTOList);
}
