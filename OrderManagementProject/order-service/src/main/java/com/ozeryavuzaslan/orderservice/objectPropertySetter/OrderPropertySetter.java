package com.ozeryavuzaslan.orderservice.objectPropertySetter;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.payments.StripePaymentResponseDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.orderservice.model.Order;

import java.util.List;

public interface OrderPropertySetter {
    Order setSomeProperties(OrderDTO orderDTO);
    void setSomeProperties(OrderDTO orderDTO, double totalPriceWithoutTax, double totalPriceWithDiscountWithoutTax,
                           double totalPrice, double totalPriceWithDiscount, double taxRate);
    void setSomeProperties(OrderDTO orderDTO, StripePaymentResponseDTO stripePaymentResponseDTO);
    void setSomeProperties(List<ReservedStockDTO> reservedStockDTOList, Order order);
    void setReserveType(OrderDTO orderDTO);
    void setOrderStatusAsPreparing(Order order);
    void setOrderStatusAsCanceled(Order order);
    void setOrderStatusAsInCargo(Order order);
    void setOrderStatusAsDelivered(Order order);
}
