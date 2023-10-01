package com.ozeryavuzaslan.orderservice.service.impl;

import com.google.gson.reflect.TypeToken;
import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.DecreaseStockQuantityDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.enums.StockAim;
import com.ozeryavuzaslan.orderservice.client.RevenueServiceClient;
import com.ozeryavuzaslan.orderservice.client.StockServiceClient;
import com.ozeryavuzaslan.orderservice.repository.OrderRepository;
import com.ozeryavuzaslan.orderservice.service.OrderService;
import feign.FeignException;
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
    private final RevenueServiceClient revenueServiceClient;

    @Override
    public OrderDTO takeOrder(OrderDTO orderDTO) {
        Type decreaseStockQuantityListType = new TypeToken<List<DecreaseStockQuantityDTO>>() {}.getType();
        List<DecreaseStockQuantityDTO> decreaseStockQuantityDTOList = modelMapper.map(orderDTO.getOrderStockDTOList(), decreaseStockQuantityListType);

        try {
            List<StockDTO> stockDTOList = stockServiceClient.modifyOrGetProductList(decreaseStockQuantityDTOList, StockAim.GET);
        } catch (FeignException feignException) {

        }

        return null;
    }
}