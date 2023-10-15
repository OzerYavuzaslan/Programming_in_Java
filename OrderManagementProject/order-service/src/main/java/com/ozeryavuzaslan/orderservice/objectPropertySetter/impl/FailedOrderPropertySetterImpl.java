package com.ozeryavuzaslan.orderservice.objectPropertySetter.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.orderservice.dto.FailedOrderDTO;
import com.ozeryavuzaslan.orderservice.dto.FailedOrderStockDTO;
import com.ozeryavuzaslan.orderservice.model.FailedOrder;
import com.ozeryavuzaslan.orderservice.model.FailedOrderStock;
import com.ozeryavuzaslan.orderservice.model.enums.PaymentRollbackState;
import com.ozeryavuzaslan.orderservice.model.enums.RollbackPhase;
import com.ozeryavuzaslan.orderservice.model.enums.StockRollbackState;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.FailedOrderPropertySetter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FailedOrderPropertySetterImpl implements FailedOrderPropertySetter {
    private final ModelMapper modelMapper;

    @Override
    public FailedOrderDTO setSomeProperties(List<ReservedStockDTO> reservedStockDTOList) {
        List<FailedOrderStockDTO> failedOrderStockDTOList = new ArrayList<>();

        for (ReservedStockDTO reservedStockDTO : reservedStockDTOList) {
            FailedOrderStockDTO failedOrderStockDTO = getFailedOrderStockDTO(reservedStockDTO);
            failedOrderStockDTOList.add(failedOrderStockDTO);
        }

        FailedOrderDTO failedOrderDTO = new FailedOrderDTO();
        failedOrderDTO.setOrderid(reservedStockDTOList.get(0).getOrderid());
        failedOrderDTO.setOrderRollbackStatus(false);
        failedOrderDTO.setRollbackPhase(RollbackPhase.PHASE_1);
        failedOrderDTO.setPaymentRollbackState(PaymentRollbackState.NOT_NEEDED);
        failedOrderDTO.setFailedOrderStockList(failedOrderStockDTOList);
        return failedOrderDTO;
    }

    @Override
    public void setSomeProperties(FailedOrder failedOrder, FailedOrderDTO failedOrderDTO) {
        List<FailedOrderStock> failedOrderStocks = new ArrayList<>();

        for (FailedOrderStockDTO dto : failedOrderDTO.getFailedOrderStockList()) {
            FailedOrderStock failedOrderStock = modelMapper.map(dto, FailedOrderStock.class);
            failedOrderStock.setFailedOrder(failedOrder);
            failedOrderStocks.add(failedOrderStock);
        }

        failedOrder.setFailedOrderStockList(failedOrderStocks);
    }

    @Override
    public FailedOrderDTO setSomeProperties(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList) {
        FailedOrderDTO failedOrderDTO = setSomeProperties(reservedStockDTOList);
        failedOrderDTO.setRollbackPhase(RollbackPhase.PHASE_2);
        failedOrderDTO.setPaymentRollbackState(PaymentRollbackState.NOT_REFUNDED);
        return failedOrderDTO;
    }

    @NotNull
    private static FailedOrderStockDTO getFailedOrderStockDTO(ReservedStockDTO reservedStockDTO) {
        FailedOrderStockDTO failedOrderStockDTO = new FailedOrderStockDTO();
        failedOrderStockDTO.setStockid(reservedStockDTO.getStock().getId());
        failedOrderStockDTO.setReserveStockID(reservedStockDTO.getId());
        failedOrderStockDTO.setFailedOrderID(reservedStockDTO.getOrderid());
        failedOrderStockDTO.setReserveType(reservedStockDTO.getReserveType());
        failedOrderStockDTO.setReserveRollbackStatus(false);
        failedOrderStockDTO.setStockRollbackState(StockRollbackState.NOT_NEEDED);
        return failedOrderStockDTO;
    }
}
