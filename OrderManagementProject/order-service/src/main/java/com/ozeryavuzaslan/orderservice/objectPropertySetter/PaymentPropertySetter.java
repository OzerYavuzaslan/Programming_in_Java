package com.ozeryavuzaslan.orderservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.orderservice.dto.PaymentRequestDTOForPaymentService;
import com.ozeryavuzaslan.orderservice.dto.RefundRequestDTOForPaymentService;

public interface PaymentPropertySetter {
    void setSomeProperties(OrderDTO orderDTO, PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService);
    void setSomeProperties(OrderDTO orderDTO, RefundRequestDTOForPaymentService refundRequestDTOForPaymentService);
}
