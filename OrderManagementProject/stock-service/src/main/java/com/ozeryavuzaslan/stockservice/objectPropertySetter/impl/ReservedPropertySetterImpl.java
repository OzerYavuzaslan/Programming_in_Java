package com.ozeryavuzaslan.stockservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.stockservice.model.ReservedStock;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.ReservedStockPropertySetter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ReservedPropertySetterImpl implements ReservedStockPropertySetter{
    @Override
    public Map<UUID, ReservedStock> setSomeProperties(List<ReservedStock> reservedStockList) {
        Map<UUID, ReservedStock> tmpReservedStockMap = new HashMap<>();

        for (ReservedStock reservedStock : reservedStockList)
            tmpReservedStockMap.put(reservedStock.getStock().getProductCode(), reservedStock);

        return tmpReservedStockMap;
    }
}
