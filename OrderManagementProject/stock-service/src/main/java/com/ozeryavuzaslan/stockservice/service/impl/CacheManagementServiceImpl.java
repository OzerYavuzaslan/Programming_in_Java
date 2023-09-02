package com.ozeryavuzaslan.stockservice.service.impl;

import com.ozeryavuzaslan.stockservice.service.CacheManagementService;
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
    public void clearCache(){
    }

    @Override
    public void clearCache(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);

        if (cache != null)
            cache.clear();
    }
}