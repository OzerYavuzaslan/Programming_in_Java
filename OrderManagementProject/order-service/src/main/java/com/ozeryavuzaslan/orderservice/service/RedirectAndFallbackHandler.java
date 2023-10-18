package com.ozeryavuzaslan.orderservice.service;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaymentRequestDTOForPaymentService;
import com.ozeryavuzaslan.basedomains.dto.payments.RefundRequestDTOForPaymentService;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import feign.Response;

import java.util.List;

public interface RedirectAndFallbackHandler {
    Response redirectGetSpecificTaxRate(List<ReservedStockDTO> reservedStockDTOList);
    Response redirectReserveStocks(List<ReservedStockDTO> reservedStockDTOList);
    Response redirectMakePayment(OrderDTO orderDTO, PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService, List<ReservedStockDTO> reservedStockDTOList);
    Response redirectDecreaseStocks(List<ReservedStockDTO> reservedStockDTOList ,OrderDTO orderDTO);
    Response redirectRollbackReservedStocks(List<ReservedStockDTO> reservedStockDTOList);
    Response redirectRollbackPayment(OrderDTO orderDTO, RefundRequestDTOForPaymentService refundRequestDTOForPaymentService);
    Response redirectRollbackStock(List<StockDTO> stockDTOList);
}
