package com.ozeryavuzaslan.stockservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.stocks.CategoryWithoutUUIDDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.DecreaseStockQuantityDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockWithoutUUIDDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.enums.StockAim;
import com.ozeryavuzaslan.basedomains.util.CacheManagementService;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
    private final CacheManagementService cacheManagementService;
    private static boolean isCacheRefresh = true;

    @Value("${stock.amount.not.enough}")
    private String stockAmountNotEnough;

    @Value("${stock.not.found}")
    private String stockNotFound;

    @Value("${stock.list.not.found}")
    private String stocksNotFound;

    @Value("${stock.cache.name}")
    private String stockCacheName;

    //Such a bad relationship between entities as well as the operations... Do not do what I did here! lol...
    @Override
    @CachePut(value = "stocks", key = "#stockWithoutUUIDDTO.productName")
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

        isCacheRefresh = false;
        return modelMapper.map(stockRepository.save(modelMapper.map(stockWithoutUUIDDTO, Stock.class)), StockDTO.class);
    }

    @Override
    @CachePut(value = "stocks", key = "#stockDTO.productCode")
    public StockDTO updateStock(StockDTO stockDTO){
        return saveOrUpdateStock(modelMapper.map(stockDTO, StockWithoutUUIDDTO.class));
    }

    @Override
    @Cacheable(value = "stocks", key = "#productCode")
    public StockDTO getByProductCode(UUID productCode) {
        return getStockByProductCode(productCode);
    }

    @Override
    @Cacheable(value = "stocks", key = "#productID")
    public StockDTO getByProductID(long productID) {
        return modelMapper.map(stockRepository.findById(productID).orElseThrow(() -> new StockNotFoundException(stockNotFound)), StockDTO.class);
    }

    @Override
    @Cacheable(value = "stocks", key = "#productName")
    public StockDTO getByProductName(String productName) {
        return modelMapper.map(stockRepository.findByProductName(productName).orElseThrow(() -> new StockNotFoundException(stockNotFound)), StockDTO.class);
    }

    @Override
    @Cacheable(value = "stocks")
    @SuppressWarnings("ConstantConditions")
    public List<StockDTO> getStockList(Pageable pageable){
        return stockRepository
                .findAll(pageable)
                .stream()
                .map(stock -> modelMapper.map(stock, StockDTO.class))
                .toList();
    }

    @Override
    @Cacheable(value = "stocks")
    public List<StockDTO> getStockList() {
        Pageable pageable = PageRequest.of(0, 1000);
        return getStockList(pageable);
    }

    @Override
    @Transactional
    @CacheEvict(value = "stocks", key = "#productCode")
    public void deleteStockByProductCode(UUID productCode) {
        stockRepository.delete(modelMapper.map(getStockByProductCode(productCode), Stock.class));
        isCacheRefresh = false;
    }

    @Override
    @CachePut(value = "stocks", key = "#productCode")
    public StockDTO getSpecificStocks(UUID productCode, int quantity) {
        StockDTO stockDTO = getStockByProductCode(productCode);

        if (stockDTO.getQuantity() < quantity)
            throw new ProductAmountNotEnoughException(stockAmountNotEnough);

        stockDTO.setQuantity(stockDTO.getQuantity() - quantity);
        return saveOrUpdateStock(modelMapper.map(stockDTO, StockWithoutUUIDDTO.class));
    }

    @Override
    public List<StockDTO> getSpecificStocks(List<DecreaseStockQuantityDTO> decreaseStockQuantityDTOList, StockAim stockAim) {
        Optional<List<Stock>> optionalStockList = stockRepository.findByProductCodeIn(decreaseStockQuantityDTOList.stream().map(DecreaseStockQuantityDTO::getProductCode).toList());

        if (optionalStockList.isEmpty())
            throw new StockNotFoundException(stocksNotFound);

        List<Stock> stockList = optionalStockList.get();

        if (stockList.size() != decreaseStockQuantityDTOList.size())
            stockList.forEach(this::findNotEnoughStock);

/*
        Collections.sort(decreaseStockQuantityDTOList, new Comparator<DecreaseStockQuantityDTO>() {
            @Override
            public int compare(DecreaseStockQuantityDTO o1, DecreaseStockQuantityDTO o2) {
                return o1.getProductCode().toString().compareTo(o2.getProductCode().toString());
            }
        });
*/
        decreaseStockQuantityDTOList.sort(Comparator.comparing(stockQuantityDTO -> stockQuantityDTO.getProductCode().toString()));

        for (int i = 0; i < decreaseStockQuantityDTOList.size(); i++) {
            int stockQuantity = stockList.get(i).getQuantity();
            int requestedStockQuantity = decreaseStockQuantityDTOList.get(i).getQuantity();

            if (stockQuantity < requestedStockQuantity)
                throw new ProductAmountNotEnoughException(stockAmountNotEnough + " (" + stockList.get(i).getProductName() + ")");

            if (stockAim.equals(StockAim.DECREASE))
                stockList.get(i).setQuantity(stockQuantity - requestedStockQuantity);
        }

        if (stockAim.equals(StockAim.DECREASE))
            return decreaseStocksQuantity(stockList);

        return stockList.stream()
                .map(stock -> modelMapper.map(stock, StockDTO.class))
                .toList();
    }

    @Override
    public void checkStockServiceCacheState(){
        isCacheRefresh = cacheManagementService.releaseCache(isCacheRefresh, stockCacheName);
    }

    private List<StockDTO> decreaseStocksQuantity(List<Stock> stockList){
        isCacheRefresh = false;
        return stockRepository
                .saveAll(stockList)
                .stream()
                .map(stock -> modelMapper.map(stock, StockDTO.class))
                .toList();
    }

    private StockDTO getStockByProductCode(UUID productCode){
        return modelMapper
                .map(stockRepository
                        .findByProductCode(productCode).orElseThrow(() -> new StockNotFoundException(stockNotFound)), StockDTO.class);
    }

    private void findNotEnoughStock(Stock stock){
        stockRepository
                .findByProductCode(stock
                        .getProductCode())
                .orElseThrow(() -> new StockNotFoundException(stockNotFound + " (" + stock.getProductName() + ")"));
    }
}