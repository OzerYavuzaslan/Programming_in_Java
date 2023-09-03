package com.ozeryavuzaslan.stockservice.util;

import com.ozeryavuzaslan.basedomains.util.CacheManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheManagementServiceImpl implements CacheManagementService {
    private final CacheManager cacheManager;

    @Override
    @CacheEvict(value = "stocks", allEntries = true)
    public void clearStockCache(){
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public void clearCategoryCache() {
    }

    @Override
    public void clearStockCache(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);

        if (cache != null)
            cache.clear();
    }
}