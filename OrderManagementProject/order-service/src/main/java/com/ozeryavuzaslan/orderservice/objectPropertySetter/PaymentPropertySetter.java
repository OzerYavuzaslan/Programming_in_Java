package com.ozeryavuzaslan.orderservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.PaymentRequestDTOForPaymentService;

public interface PaymentPropertySetter {
    void setSomeProperties(OrderDTO orderDTO, PaymentRequestDTOForPaymentService paymentRequestDTOForPaymentService);
}
