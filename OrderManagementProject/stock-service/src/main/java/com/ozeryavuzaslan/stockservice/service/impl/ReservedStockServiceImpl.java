package com.ozeryavuzaslan.stockservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.enums.ReserveType;
import com.ozeryavuzaslan.basedomains.util.TypeFactoryHelper;
import com.ozeryavuzaslan.stockservice.exception.ProductAmountNotEnoughException;
import com.ozeryavuzaslan.stockservice.exception.ReservedStockNotFound;
import com.ozeryavuzaslan.stockservice.exception.StockNotFoundException;
import com.ozeryavuzaslan.stockservice.model.ReservedStock;
import com.ozeryavuzaslan.stockservice.model.Stock;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.ReservedStockPropertySetter;
import com.ozeryavuzaslan.stockservice.repository.ReservedStockRepository;
import com.ozeryavuzaslan.stockservice.repository.StockRepository;
import com.ozeryavuzaslan.stockservice.service.ReservedStockService;
import com.ozeryavuzaslan.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservedStockServiceImpl implements ReservedStockService {
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final StockService stockService;
    private final StockRepository stockRepository;
    private final ReservedStockRepository reservedStockRepository;
    private final ReservedStockPropertySetter reservedStockPropertySetter;

    @Value("${stock.amount.not.enough}")
    private String stockAmountNotEnough;

    @Value("${stock.not.found}")
    private String stockNotFound;

    @Value("${stock.list.not.found}")
    private String stocksNotFound;

    @Value("${stock.reserved.stock.list.not.found}")
    private String reservedStocksNotFound;

    @Override
    @Transactional
    public List<ReservedStockDTO> reserveStock(List<ReservedStockDTO> reservedStockDTOList) {
        List<Stock> stockList = stockRepository.findByProductCodeInOrderByProductCodeAsc(reservedStockDTOList.stream().map(ReservedStockDTO::getProductCode).toList());

        if (stockList.isEmpty())
            throw new StockNotFoundException(stocksNotFound);

        Set<UUID> productCodesFromDTO = reservedStockDTOList
                .stream()
                .map(ReservedStockDTO::getProductCode)
                .collect(Collectors.toSet());

        for (Stock stock : stockList)
            if (!productCodesFromDTO.contains(stock.getProductCode()))
                throw new StockNotFoundException(stockNotFound + " (" + stock.getProductName() + ")");

        List<ReservedStock> reservedStockList = reservedStockRepository.findByStockInAndReserveTypeOrderByStock_ProductCodeAsc(stockList, ReserveType.RESERVED);
        Map<UUID, ReservedStock> reservedStockMap = null;

        if (!reservedStockList.isEmpty())
            reservedStockMap = reservedStockPropertySetter.setSomeProperties(reservedStockList);

        reservedStockDTOList.sort(Comparator.comparing(stockQuantityDTO -> stockQuantityDTO.getProductCode().toString()));
        List<ReservedStock> reservedStockListToSave = new ArrayList<>();

        for (int i = 0; i < reservedStockDTOList.size(); i++) {
            UUID currentProductCode = reservedStockDTOList.get(i).getProductCode();
            int reservedQuantity = (reservedStockMap != null && reservedStockMap.containsKey(currentProductCode)) ? reservedStockMap.get(currentProductCode).getQuantity() : 0;
            int stockQuantity = stockList.get(i).getQuantity();
            int requestedAndReservedStockQuantity = reservedStockDTOList.get(i).getQuantity() + reservedQuantity;

            if (stockQuantity < requestedAndReservedStockQuantity)
                throw new ProductAmountNotEnoughException(stockAmountNotEnough + " (" + stockList.get(i).getProductName() + ")");

            StockDTO stockDTO = modelMapper.map(stockList.get(i), StockDTO.class);
            reservedStockDTOList.get(i).setStock(stockDTO);
            reservedStockListToSave.add(modelMapper.map(reservedStockDTOList.get(i), ReservedStock.class));
        }

        return reservedStockRepository
                .saveAll(reservedStockListToSave)
                .stream()
                .map(reservedStock -> modelMapper.map(reservedStock, ReservedStockDTO.class))
                .toList();
    }

    @Override
    public List<ReservedStockDTO> rollbackReserveStock(List<ReservedStockDTO> reservedStockDTOList) {
        List<ReservedStock> reservedStockList = new ArrayList<>();

        for (ReservedStockDTO reservedStockDTO : reservedStockDTOList) {
            reservedStockDTO.setReserveType(ReserveType.RESERVE_CANCELED);
            reservedStockList.add(modelMapper.map(reservedStockDTO, ReservedStock.class));
        }

        return reservedStockRepository
                .saveAll(reservedStockList)
                .stream()
                .map(reservedStock -> modelMapper.map(reservedStock, ReservedStockDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public List<ReservedStockDTO> rollbackStocksAndReserveStocks(long orderID) {
        List<ReservedStock> reservedStockList = getReservedStocks(orderID);
        List<Stock> stockList = new ArrayList<>();

        reservedStockPropertySetter.setSomeProperties(stockList, reservedStockList);
        reservedStockRepository.saveAll(reservedStockList);

        Type stockDTOType = TypeFactoryHelper.constructCollectionType(StockDTO.class);
        stockService.updateStocks(modelMapper.map(stockList, stockDTOType));

        Type reserveStockListDTOType = TypeFactoryHelper.constructCollectionType(ReservedStockDTO.class);
        return modelMapper.map(reservedStockList, reserveStockListDTOType);
    }

    @Override
    public List<ReservedStockDTO> getByOrderID(long orderID) {
        List<ReservedStock> reservedStockList = getReservedStocks(orderID);
        Type reserveStockListDTOType = TypeFactoryHelper.constructCollectionType(ReservedStockDTO.class);
        return modelMapper.map(reservedStockList, reserveStockListDTOType);
    }

    private List<ReservedStock> getReservedStocks(long orderID) {
        List<ReservedStock> reservedStockList = reservedStockRepository.findByOrderid(orderID);

        if (reservedStockList.isEmpty())
            throw new ReservedStockNotFound(reservedStocksNotFound);

        return reservedStockList;
    }
}
