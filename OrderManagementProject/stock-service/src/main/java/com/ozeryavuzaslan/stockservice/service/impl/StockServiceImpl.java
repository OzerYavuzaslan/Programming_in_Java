package com.ozeryavuzaslan.stockservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.CategoryDTO;
import com.ozeryavuzaslan.basedomains.dto.StockDTO;
import com.ozeryavuzaslan.stockservice.exception.QuantityAmountNotEnoughException;
import com.ozeryavuzaslan.stockservice.exception.StockNotFoundException;
import com.ozeryavuzaslan.stockservice.model.Category;
import com.ozeryavuzaslan.stockservice.model.Stock;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.StockPropertySetter;
import com.ozeryavuzaslan.stockservice.repository.CategoryRepository;
import com.ozeryavuzaslan.stockservice.repository.StockRepository;
import com.ozeryavuzaslan.stockservice.service.StockService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.ozeryavuzaslan.basedomains.util.Constants.QUANTITY_AMOUNT_NOT_ENOUGH;
import static com.ozeryavuzaslan.basedomains.util.Constants.STOCK_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final ModelMapper modelMapper;
    private final StockRepository stockRepository;
    private final CategoryRepository categoryRepository;
    private final StockPropertySetter stockPropertySetter;

    //Such a bad relationship between entities as well as the operations... Do not do what I did here! lol...
    @Override
    public StockDTO saveOrUpdateStock(StockDTO stockDTO) {
        Optional<Stock> stock = stockRepository.findByProductName(stockDTO.getProductName());

        if (stock.isEmpty()) {
            Optional<Category> category = categoryRepository.findByName(stockDTO.getCategory().getName());
            boolean isCategoryPresent = category.isPresent();
            stockPropertySetter.setSomeProperties(stockDTO, true, isCategoryPresent);

            if (isCategoryPresent)
                stockDTO.setCategory(modelMapper.map(category, CategoryDTO.class));
            else
                stockDTO.setCategory(modelMapper.map(categoryRepository.save(modelMapper.map(stockDTO.getCategory(), Category.class)), CategoryDTO.class));
        }
        else {
            stockPropertySetter.setSomeProperties(stockDTO, false, false);
            stockPropertySetter.setSomeProperties(stock.get(), stockDTO);
        }

        return modelMapper
                .map(stockRepository
                                .save(modelMapper
                                        .map(stockDTO, Stock.class)),
                        StockDTO.class);
    }

    @Override
    public StockDTO getByProductID(long productID) {
        return modelMapper
                .map(stockRepository
                        .findById(productID).orElseThrow(() -> new StockNotFoundException(STOCK_NOT_FOUND)),
                        StockDTO.class);
    }

    @Override
    public StockDTO getByProductName(String productName) {
        return getProduct(productName);
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
    @Transactional
    public void deleteStockByProductName(String productName) {
        stockRepository
                .deleteByProductName(productName)
                .orElseThrow(() -> new StockNotFoundException(STOCK_NOT_FOUND));
    }

    @Override
    public StockDTO decreaseStockQuantity(String productName, int quantityAmount) {
        StockDTO stockDTO = getProduct(productName);

        if (stockDTO.getQuantity() < quantityAmount)
            throw new QuantityAmountNotEnoughException(QUANTITY_AMOUNT_NOT_ENOUGH);

        stockDTO.setQuantity(stockDTO.getQuantity() - quantityAmount);

        return saveOrUpdateStock(stockDTO);
    }

    private StockDTO getProduct(String productName){
        return modelMapper
                .map(stockRepository
                                .findByProductName(productName)
                                .orElseThrow(() -> new StockNotFoundException(STOCK_NOT_FOUND)),
                        StockDTO.class);
    }
}