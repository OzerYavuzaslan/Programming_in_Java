package com.ozeryavuzaslan.stockservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.CategoryWithoutUUIDDTO;
import com.ozeryavuzaslan.basedomains.dto.StockDTO;
import com.ozeryavuzaslan.basedomains.dto.StockWithoutUUIDDTO;
import com.ozeryavuzaslan.stockservice.exception.ProductAmountNotEnoughException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final ModelMapper modelMapper;
    private final StockRepository stockRepository;
    private final CategoryRepository categoryRepository;
    private final StockPropertySetter stockPropertySetter;

    @Value("${stock.amount.not.enough}")
    private String stockAmountNotEnough;

    @Value("${stock.not.found}")
    private String stockNotFound;

    //Such a bad relationship between entities as well as the operations... Do not do what I did here! lol...
    @Override
    public StockDTO saveOrUpdateStock(StockWithoutUUIDDTO stockWithoutUUIDDTO) {
        Optional<Stock> stock = stockRepository.findByProductCode(stockWithoutUUIDDTO.getProductCode());

        if (stock.isEmpty()) {
            Optional<Category> category;

            if (stockWithoutUUIDDTO.getCategory().getCategoryCode() == null)
                category = categoryRepository.findByName(stockWithoutUUIDDTO.getCategory().getName());
            else
                category = categoryRepository.findByCategoryCode(stockWithoutUUIDDTO.getCategory().getCategoryCode());

            boolean isCategoryPresent = category.isPresent();
            stockPropertySetter.setSomeProperties(stockWithoutUUIDDTO, true, isCategoryPresent);

            if (isCategoryPresent)
                stockWithoutUUIDDTO.setCategory(modelMapper.map(category, CategoryWithoutUUIDDTO.class));
            else
                stockWithoutUUIDDTO.setCategory(modelMapper.map(categoryRepository.save(modelMapper.map(stockWithoutUUIDDTO.getCategory(), Category.class)), CategoryWithoutUUIDDTO.class));
        }
        else {
            stockPropertySetter.setSomeProperties(stockWithoutUUIDDTO, false, false);
            stockPropertySetter.setSomeProperties(stock.get(), stockWithoutUUIDDTO);
        }

        return modelMapper
                .map(stockRepository
                                .save(modelMapper
                                        .map(stockWithoutUUIDDTO, Stock.class)),
                        StockDTO.class);
    }

    @Override
    public StockDTO getByProductID(long productID) {
        return modelMapper
                .map(stockRepository
                        .findById(productID).orElseThrow(() -> new StockNotFoundException(stockNotFound)),
                        StockDTO.class);
    }

    @Override
    public StockDTO getByProductCode(UUID productCode) {
        return getProduct(productCode);
    }

    @Override
    public StockDTO getByProductName(String productName) {
        return modelMapper
                .map(stockRepository
                                .findByProductName(productName)
                                .orElseThrow(() -> new StockNotFoundException(stockNotFound)),
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
    @Transactional
    public void deleteStockByProductName(UUID productCode) {
        stockRepository
                .deleteByProductCode(productCode)
                .orElseThrow(() -> new StockNotFoundException(stockNotFound));
    }

    @Override
    public StockDTO decreaseStockQuantity(UUID productCode, int quantityAmount) {
        StockDTO stockDTO = getProduct(productCode);

        if (stockDTO.getQuantity() < quantityAmount)
            throw new ProductAmountNotEnoughException(stockAmountNotEnough);

        stockDTO.setQuantity(stockDTO.getQuantity() - quantityAmount);

        return saveOrUpdateStock(modelMapper.map(stockDTO, StockWithoutUUIDDTO.class));
    }

    private StockDTO getProduct(UUID productCode){
        return modelMapper
                .map(stockRepository
                                .findByProductCode(productCode)
                                .orElseThrow(() -> new StockNotFoundException(stockNotFound)),
                        StockDTO.class);
    }
}