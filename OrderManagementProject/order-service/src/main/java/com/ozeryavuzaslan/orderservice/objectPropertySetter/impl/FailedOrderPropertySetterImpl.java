package com.ozeryavuzaslan.orderservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.orderservice.dto.FailedOrderDTO;
import com.ozeryavuzaslan.orderservice.dto.FailedOrderStockDTO;
import com.ozeryavuzaslan.orderservice.model.enums.RollbackPhase;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.FailedOrderPropertySetter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FailedOrderPropertySetterImpl implements FailedOrderPropertySetter {
    @Override
    public FailedOrderDTO setSomeProperties(List<ReservedStockDTO> reservedStockDTOList) {
        List<FailedOrderStockDTO> failedOrderStockDTOList = new ArrayList<>();

        for (ReservedStockDTO reservedStockDTO : reservedStockDTOList){
            FailedOrderStockDTO failedOrderStockDTO = new FailedOrderStockDTO();
            failedOrderStockDTO.setStockid(reservedStockDTO.getStock().getId());
            failedOrderStockDTO.setReserveStockID(reservedStockDTO.getId());
            failedOrderStockDTO.setFailedOrderID(reservedStockDTO.getOrderid());
            failedOrderStockDTO.setReserveType(reservedStockDTO.getReserveType());
            failedOrderStockDTO.setReserveRollbackStatus(false);
            failedOrderStockDTOList.add(failedOrderStockDTO);
        }

        FailedOrderDTO failedOrderDTO = new FailedOrderDTO();
        failedOrderDTO.setOrderid(reservedStockDTOList.get(0).getOrderid());
        failedOrderDTO.setOrderRollbackStatus(false);
        failedOrderDTO.setRollbackPhase(RollbackPhase.PHASE_1);
        failedOrderDTO.setFailedOrderStockList(failedOrderStockDTOList);
        return failedOrderDTO;
    }
}
