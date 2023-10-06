package com.ozeryavuzaslan.orderservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.orderservice.model.Order;

public interface OrderPropertySetter {
    Order setSomeProperties(OrderDTO orderDTO);
    void setSomeProperties(OrderDTO orderDTO, double totalPrice, double totalPriceWithoutTax, double taxRate);
    void setSomeProperties(OrderDTO orderDTO, StripePaymentResponseDTO stripePaymentResponseDTO);
}
