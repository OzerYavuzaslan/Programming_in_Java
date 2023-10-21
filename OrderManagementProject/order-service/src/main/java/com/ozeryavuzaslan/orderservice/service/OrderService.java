package com.ozeryavuzaslan.orderservice.service;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;

public interface OrderService {
    OrderDTO takeOrder(OrderDTO orderDTO) throws Exception;
    OrderDTO getByOrderID(long orderID);
    OrderDTO prepareByOrderID(long orderID);
    OrderDTO cancelByOrderID(long orderID) throws Exception;
    OrderDTO giveToCargoCompanyByOrderID(long orderID);
    OrderDTO deliverByOrderID(long orderID);
}
