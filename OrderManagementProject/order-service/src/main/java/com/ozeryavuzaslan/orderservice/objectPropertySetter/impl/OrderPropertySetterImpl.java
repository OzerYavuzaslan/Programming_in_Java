package com.ozeryavuzaslan.orderservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.orders.enums.OrderStatusType;
import com.ozeryavuzaslan.basedomains.dto.payments.enums.PaymentStatus;
import com.ozeryavuzaslan.orderservice.model.Order;
import com.ozeryavuzaslan.orderservice.model.OrderStock;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.OrderPropertySetter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPropertySetterImpl implements OrderPropertySetter {
    private final ModelMapper modelMapper;

    @Override
    public Order setSomeProperties(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setOrderStatusType(OrderStatusType.TAKEN);

        if (order.getOrderStockList() != null)
            for (OrderStock orderStock : order.getOrderStockList())
                orderStock.setOrder(order);

        return order;
    }
}
