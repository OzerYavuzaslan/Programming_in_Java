package com.ozeryavuzaslan.stockservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.stockservice.model.ReservedStock;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.ReservedStockPropertySetter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReservedPropertySetterImpl implements ReservedStockPropertySetter{
    private final ModelMapper modelMapper;

    @Override
    public Map<UUID, ReservedStock> setSomeProperties(List<ReservedStock> reservedStockList) {
        Map<UUID, ReservedStock> tmpReservedStockMap = new HashMap<>();

        for (ReservedStock reservedStock : reservedStockList)
            tmpReservedStockMap.put(reservedStock.getStock().getProductCode(), reservedStock);

        return tmpReservedStockMap;
    }
}
