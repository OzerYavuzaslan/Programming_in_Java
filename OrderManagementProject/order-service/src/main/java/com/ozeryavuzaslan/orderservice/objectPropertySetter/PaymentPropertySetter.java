package com.ozeryavuzaslan.orderservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaymentRequestDTOForPaymentService;
import com.ozeryavuzaslan.basedomains.dto.payments.RefundRequestDTOForPaymentService;

public interface PaymentPropertySetter {
    void setSomeProperties(OrderDTO orderDTO, PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService);
    void setSomeProperties(OrderDTO orderDTO, RefundRequestDTOForPaymentService refundRequestDTOForPaymentService);
}
