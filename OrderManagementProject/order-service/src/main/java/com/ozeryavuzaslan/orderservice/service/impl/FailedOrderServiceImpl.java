package com.ozeryavuzaslan.orderservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.orders.OrderDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.orderservice.dto.FailedOrderDTO;
import com.ozeryavuzaslan.orderservice.exception.OrderNotFoundException;
import com.ozeryavuzaslan.orderservice.model.FailedOrder;
import com.ozeryavuzaslan.orderservice.model.enums.RollbackPhase;
import com.ozeryavuzaslan.orderservice.model.enums.RollbackReason;
import com.ozeryavuzaslan.orderservice.objectPropertySetter.FailedOrderPropertySetter;
import com.ozeryavuzaslan.orderservice.repository.FailedOrderRepository;
import com.ozeryavuzaslan.orderservice.service.FailedOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Service
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FailedOrderServiceImpl implements FailedOrderService {
    private final ModelMapper modelMapper;
    private final FailedOrderRepository failedOrderRepository;
    private final FailedOrderPropertySetter failedOrderPropertySetter;

    @Value("${order.get.by.id.endpoint}")
    private String orderNotFoundMsg;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertFailedOrderAndRollbackPhase(List<ReservedStockDTO> reservedStockDTOList) {
        FailedOrderDTO failedOrderDTO = failedOrderPropertySetter.setSomeProperties(reservedStockDTOList, RollbackPhase.PHASE_1, RollbackReason.SERVICE_EXCEPTION);
        insertFailedOrderAndRollbackPhase(failedOrderDTO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertFailedOrderAndRollbackPhase(OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList) {
        FailedOrderDTO failedOrderDTO = failedOrderPropertySetter.setSomeProperties(orderDTO, reservedStockDTOList, RollbackPhase.PHASE_2, RollbackReason.SERVICE_EXCEPTION);
        insertFailedOrderAndRollbackPhase(failedOrderDTO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertFailedOrderAndRollbackPhase(List<ReservedStockDTO> reservedStockDTOList, OrderDTO orderDTO) {
        FailedOrderDTO failedOrderDTO = failedOrderPropertySetter.setSomeProperties(orderDTO, reservedStockDTOList, RollbackPhase.PHASE_3, RollbackReason.SERVICE_EXCEPTION);
        insertFailedOrderAndRollbackPhase(failedOrderDTO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertFailedOrderAndRollbackPhase(RollbackReason rollbackReason, OrderDTO orderDTO, List<ReservedStockDTO> reservedStockDTOList) {
        FailedOrderDTO failedOrderDTO = failedOrderPropertySetter.setSomeProperties(orderDTO, RollbackPhase.PHASE_3, RollbackReason.CANCELED_BY_CUSTOMER, reservedStockDTOList);
        insertFailedOrderAndRollbackPhase(failedOrderDTO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertFailedOrderAndRollbackPhase(OrderDTO orderDTO) {
        FailedOrderDTO failedOrderDTO = failedOrderPropertySetter.setSomeProperties(orderDTO, RollbackPhase.PHASE_3, RollbackReason.CANCELED_BY_CUSTOMER, null);
        insertFailedOrderAndRollbackPhase(failedOrderDTO);
    }

    @Override
    public FailedOrderDTO getSpecificFailedOrder(long failedOrderID) {
        FailedOrder failedOrder = getSpecificFailedOrderByID(failedOrderID);
        return modelMapper.map(failedOrder, FailedOrderDTO.class);
    }

    private void insertFailedOrderAndRollbackPhase(FailedOrderDTO failedOrderDTO) {
        FailedOrder failedOrder = modelMapper.map(failedOrderDTO, FailedOrder.class);
        failedOrderPropertySetter.setSomeProperties(failedOrder, failedOrderDTO);
        failedOrderRepository.save(failedOrder);
    }

    private FailedOrder getSpecificFailedOrderByID(long failedOrderID){
        return failedOrderRepository.findById(failedOrderID).orElseThrow(() -> new OrderNotFoundException(orderNotFoundMsg + " --> " + failedOrderID));
    }
}
