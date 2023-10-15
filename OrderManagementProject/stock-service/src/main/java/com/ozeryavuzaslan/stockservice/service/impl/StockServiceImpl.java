package com.ozeryavuzaslan.stockservice.service.impl;

import com.ozeryavuzaslan.basedomains.dto.stocks.CategoryWithoutUUIDDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.ReservedStockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.StockWithoutUUIDDTO;
import com.ozeryavuzaslan.basedomains.dto.stocks.enums.ReserveType;
import com.ozeryavuzaslan.basedomains.util.CacheManagementService;
import com.ozeryavuzaslan.stockservice.exception.ReservedStockNotFound;
import com.ozeryavuzaslan.stockservice.exception.StockNotFoundException;
import com.ozeryavuzaslan.stockservice.model.Category;
import com.ozeryavuzaslan.stockservice.model.ReservedStock;
import com.ozeryavuzaslan.stockservice.model.Stock;
import com.ozeryavuzaslan.stockservice.objectPropertySetter.StockPropertySetter;
import com.ozeryavuzaslan.stockservice.repository.CategoryRepository;
import com.ozeryavuzaslan.stockservice.repository.ReservedStockRepository;
import com.ozeryavuzaslan.stockservice.repository.StockRepository;
import com.ozeryavuzaslan.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final ModelMapper modelMapper;
    private final StockRepository stockRepository;
    private final CategoryRepository categoryRepository;
    private final StockPropertySetter stockPropertySetter;
    private final CacheManagementService cacheManagementService;
    private final ReservedStockRepository reservedStockRepository;
    private static boolean isCacheRefresh = false;

    @Value("${stock.list.not.found}")
    private String stocksNotFound;

    @Value("${stock.not.found}")
    private String stockNotFound;

    @Value("${stock.reserved.stock.list.not.found}")
    private String reservedStocksNotFound;

    @Value("${stock.cache.name}")
    private String stockCacheName;

    //Such a bad relationship between entities as well as the operations... Do not do what I did here! lol...
    @Override
    @CachePut(value = "stocks", key = "#stockWithoutUUIDDTO.productName")
    public StockDTO saveOrUpdateStock(StockWithoutUUIDDTO stockWithoutUUIDDTO) {
        Optional<Stock> stockOptional = stockRepository.findByProductCode(stockWithoutUUIDDTO.getProductCode());

        if (stockOptional.isEmpty()) {
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
            stockPropertySetter.setSomeProperties(stockOptional.get(), stockWithoutUUIDDTO);
        }

        Stock stock = stockRepository.save(modelMapper.map(stockWithoutUUIDDTO, Stock.class));
        isCacheRefresh = false;
        return modelMapper.map(stock, StockDTO.class);
    }

    @Override
    @CachePut(value = "stocks", key = "#stockDTO.productCode")
    public StockDTO updateStock(StockDTO stockDTO){
        return saveOrUpdateStock(modelMapper.map(stockDTO, StockWithoutUUIDDTO.class));
    }

    @Override
    @Transactional
    @Cacheable(value = "stocks")
    public List<ReservedStockDTO> decreaseStock(List<ReservedStockDTO> reservedStockDTOList) {
        List<Stock> stockList = stockRepository
                .findByIdInOrderByIdAsc(reservedStockDTOList
                        .stream()
                        .map(reservedStockDTO -> reservedStockDTO.getStock().getId())
                        .collect(Collectors.toList()));

        List<ReservedStock> reservedStockList = reservedStockRepository
                .findByIdInAndReserveTypeOrderByIdAsc(reservedStockDTOList
                        .stream()
                        .map(ReservedStockDTO::getId).toList(),
                        ReserveType.RESERVED);

        if (stockList.isEmpty())
            throw new StockNotFoundException(stocksNotFound);

        if (reservedStockList.isEmpty())
            throw new ReservedStockNotFound(reservedStocksNotFound);

        reservedStockDTOList.sort(Comparator.comparing(ReservedStockDTO::getId));
        Set<Long> stockIDListFromDTO = reservedStockDTOList
                .stream()
                .map(reservedStockDTO -> reservedStockDTO.getStock().getId())
                .collect(Collectors.toSet());

        if (reservedStockDTOList.size() != reservedStockList.size()) {
            for (ReservedStock reservedStock : reservedStockList)
                if (!stockIDListFromDTO.contains(reservedStock.getId()))
                    throw new StockNotFoundException(reservedStocksNotFound + " to decrease from the stock (" + reservedStock.getId() + ")");
        }

        for (Stock stock : stockList)
            if (!stockIDListFromDTO.contains(stock.getId()))
                throw new StockNotFoundException(stockNotFound + " to decrease from the stock (" + stock.getProductName() + ")");

        for (int i = 0; i < reservedStockDTOList.size(); i++) {
            stockList.get(i).setQuantity(stockList.get(i).getQuantity() - reservedStockDTOList.get(i).getQuantity());
            reservedStockList.get(i).setReserveType(ReserveType.STOCK_DECREASED);
        }

        stockRepository.saveAll(stockList);
        reservedStockList = reservedStockRepository.saveAll(reservedStockList);
        isCacheRefresh = false;
        return reservedStockList
                .stream()
                .map(reservedStock -> modelMapper.map(reservedStock, ReservedStockDTO.class))
                .toList();
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
        return stockRepository
                .findAll()
                .stream()
                .map(stock -> modelMapper.map(stock, StockDTO.class))
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = "stocks", key = "#productCode")
    public void deleteStockByProductCode(UUID productCode) {
        stockRepository.delete(modelMapper.map(getStockByProductCode(productCode), Stock.class));
        isCacheRefresh = false;
    }

    @Override
    public void checkStockServiceCacheState(){
        isCacheRefresh = cacheManagementService.releaseCache(isCacheRefresh, stockCacheName);
    }

    private StockDTO getStockByProductCode(UUID productCode){
        return modelMapper
                .map(stockRepository
                        .findByProductCode(productCode).orElseThrow(() -> new StockNotFoundException(stockNotFound)), StockDTO.class);
    }
}