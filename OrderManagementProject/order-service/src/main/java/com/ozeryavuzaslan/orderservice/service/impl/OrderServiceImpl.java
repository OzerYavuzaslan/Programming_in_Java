package com.ozeryavuzaslan.orderservice.service.impl;

import com.google.gson.reflect.TypeToken;
import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.orderservice.client.PaymentServiceClient;
import com.ozeryavuzaslan.orderservice.client.RevenueServiceClient;
import com.ozeryavuzaslan.orderservice.client.StockServiceClient;
import com.ozeryavuzaslan.orderservice.model.Order;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.OrderPropertySetter;
import com.ozeryavuzaslan.orderservice.repository.OrderRepository;
import com.ozeryavuzaslan.orderservice.service.OrderService;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final StockServiceClient stockServiceClient;
    private final OrderPropertySetter orderPropertySetter;
    private final RevenueServiceClient revenueServiceClient;
    private final PaymentServiceClient paymentServiceClient;

    @Override
    @Transactional
    public OrderDTO takeOrder(OrderDTO orderDTO) {
        Order order = orderPropertySetter.setSomeProperties(orderDTO);
        orderRepository.save(order);
        //TODO: STOCK-SERVICE düzeltikten sonra buralar ona göre yapılacak.
        Type decreaseStockQuantityListType = new TypeToken<List<ReservedStockDTO>>() {}.getType();
        List<ReservedStockDTO> reservedStockDTOList = modelMapper.map(orderDTO.getOrderStockList(), decreaseStockQuantityListType);

        try {
            List<StockDTO> stockDTOList = stockServiceClient.modifyOrGetProductList(reservedStockDTOList);
        } catch (FeignException feignException) {

        }

        return null;
    }
}