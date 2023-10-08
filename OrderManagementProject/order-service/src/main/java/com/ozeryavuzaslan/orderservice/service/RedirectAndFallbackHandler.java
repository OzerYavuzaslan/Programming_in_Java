package com.ozeryavuzaslan.orderservice.service;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaymentRequestDTOForPaymentService;
import com.ozeryavuzaslan.basedomains.dto.revenues.TaxRateDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;

import java.util.List;

public interface RedirectAndFallbackHandler {
    TaxRateDTO redirectGetSpecificTaxRate();
    List<ReservedStockDTO> redirectReserveStocks(List<ReservedStockDTO> reservedStockDTOList);
    void redirectMakePayment(OrderDTO orderDTO, PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService);
    List<ReservedStockDTO> redirectDecreaseStocks(List<ReservedStockDTO> reservedStockDTOList);
}
