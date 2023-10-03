package com.ozeryavuzaslan.orderservice.objectPropertySetter.impl;

import com.google.gson.reflect.TypeToken;
import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.enums.ReserveType;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.StockPropertySetter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StockPropertySetterImpl implements StockPropertySetter {
    private final ModelMapper modelMapper;

    @Override
    public List<ReservedStockDTO> setSomeProperties(OrderDTO orderDTO) {
        Type reserveStockQuantityListType = new TypeToken<List<ReservedStockDTO>>() {}.getType();
        List<ReservedStockDTO> reservedStockDTOList = modelMapper.map(orderDTO.getOrderStockList(), reserveStockQuantityListType);

        reservedStockDTOList.forEach(reservedStockDTO -> {
            reservedStockDTO.setOrderid(orderDTO.getId());
            reservedStockDTO.setReserveType(ReserveType.RESERVED);
        });

        return reservedStockDTOList;
    }
}
