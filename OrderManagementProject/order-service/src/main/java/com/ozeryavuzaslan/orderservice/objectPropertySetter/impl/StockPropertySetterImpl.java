package com.ozeryavuzaslan.orderservice.objectPropertySetter.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.enums.ReserveType;
import com.ozeryavuzaslan.basedomains.util.TypeFactoryHelper;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.StockPropertySetter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StockPropertySetterImpl implements StockPropertySetter {
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Override
    public List<ReservedStockDTO> setSomeProperties(OrderDTO orderDTO) {
        JavaType reserveStockQuantityListType = TypeFactoryHelper.constructCollectionType(ReservedStockDTO.class, objectMapper);
        List<ReservedStockDTO> reservedStockDTOList = modelMapper.map(orderDTO.getOrderStockList(), reserveStockQuantityListType);

        reservedStockDTOList.forEach(reservedStockDTO -> {
            reservedStockDTO.setOrderid(orderDTO.getId());
            reservedStockDTO.setReserveType(ReserveType.RESERVED);
        });

        return reservedStockDTOList;
    }

    @Override
    public List<StockDTO> setSomeProperties(List<ReservedStockDTO> reservedStockDTOList) {
        List<StockDTO> stockDTOList = new ArrayList<>();

        for (ReservedStockDTO reservedStockDTO : reservedStockDTOList){
            StockDTO stockDTO = new StockDTO();
            modelMapper.map(reservedStockDTO.getStock(), stockDTO);
            stockDTO.setQuantity(stockDTO.getQuantity() + reservedStockDTO.getQuantity());
            stockDTO.setUpdateDate(LocalDateTime.now());
            stockDTOList.add(stockDTO);
        }

        return stockDTOList;
    }
}
