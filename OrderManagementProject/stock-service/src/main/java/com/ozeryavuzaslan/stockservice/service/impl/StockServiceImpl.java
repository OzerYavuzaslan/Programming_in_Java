package com.ozeryavuzaslan.stockservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.StockDTO;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.StockPropertySetter;
import com.ozeryavuzaslan.stockservice.exception.CategoryNotFoundException;
import com.ozeryavuzaslan.stockservice.exception.StockNotFoundException;
import com.ozeryavuzaslan.stockservice.model.Stock;
import com.ozeryavuzaslan.stockservice.repository.CategoryRepository;
import com.ozeryavuzaslan.stockservice.repository.StockRepository;
import com.ozeryavuzaslan.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ozeryavuzaslan.basedomains.util.Constants.CATEGORY_NOT_FOUND;
import static com.ozeryavuzaslan.basedomains.util.Constants.STOCK_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final ModelMapper modelMapper;
    private final StockPropertySetter stockPropertySetter;
    private final StockRepository stockRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public StockDTO saveOrUpdateStock(StockDTO stockDTO) {
        categoryRepository
                .findByName(stockDTO
                        .getCategory()
                        .getName())
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));

        Optional<Stock> stock = stockRepository.findByProductName(stockDTO.getProductName());

        if (stock.isEmpty())
            stockDTO = stockPropertySetter.convert(stockDTO, true);
        else
            stockDTO = stockPropertySetter.convert(stockDTO, false);

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
}