package com.ozeryavuzaslan.orderservice.service;

import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;

import java.util.List;

public interface BeginSagaRollbackChain {
    void beginRollbackFromReservedStocksPhase1(List<ReservedStockDTO> reservedStockDTOList) throws Exception;
}
