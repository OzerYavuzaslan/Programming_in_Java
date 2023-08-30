package com.ozeryavuzaslan.stockservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.StockDTO;
import com.ozeryavuzaslan.stockservice.exception.StockNotFoundException;
import com.ozeryavuzaslan.stockservice.model.Stock;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.StockPropertySetter;
import com.ozeryavuzaslan.stockservice.repository.StockRepository;
import com.ozeryavuzaslan.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.ozeryavuzaslan.basedomains.util.Constants.STOCK_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final ModelMapper modelMapper;
    private final StockRepository stockRepository;
    private final StockPropertySetter stockPropertySetter;

    @Override //TODO:UPDATE YAPARKEN BÜTÜN FIELDLARIN DOLULUĞUNU KONTROL ET! DB'deki ADDDATE verisini alıp DTO'nun adddateine set edip öyle UPDATE ET!!!!
    public StockDTO saveOrUpdateStock(StockDTO stockDTO) {
        Optional<Stock> stock = stockRepository.findByProductName(stockDTO.getProductName());

        if (stock.isEmpty())
            stockDTO = stockPropertySetter.setDates(stockDTO, true);
        else
            stockDTO = stockPropertySetter.setDates(stockDTO, false);

        return modelMapper
                .map(stockRepository
                                .save(modelMapper
                                        .map(stockDTO, Stock.class)),
                        StockDTO.class);
    }

    @Override
    public StockDTO getByProductName(String productName) {
        return modelMapper
                .map(stockRepository
                        .findByProductName(productName)
                        .orElseThrow(() -> new StockNotFoundException(STOCK_NOT_FOUND)),
                        StockDTO.class);
    }

    @Override
    public List<StockDTO> getStockList() {
        return stockRepository
                .findAll()
                .stream()
                .map(stock -> modelMapper.map(stock, StockDTO.class))
                .toList();
    }

    @Override
    public void deleteStockByProductName(String productName) {
        stockRepository
                .deleteByProductName(productName)
                .orElseThrow(() -> new StockNotFoundException(STOCK_NOT_FOUND));
    }
}