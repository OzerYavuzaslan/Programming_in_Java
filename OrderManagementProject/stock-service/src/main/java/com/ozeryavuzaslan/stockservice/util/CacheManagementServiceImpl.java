package com.ozeryavuzaslan.stockservice.util;

import com.ozeryavuzaslan.basedomains.util.CacheManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheManagementServiceImpl implements CacheManagementService {
    private final CacheManager cacheManager;

    @Override
    public void clearCache(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);

        if (cache != null)
            cache.clear();
    }

    @Override
    public boolean releaseCache(boolean isCacheRefresh, String cacheName){
        if (!isCacheRefresh)
            clearCache(cacheName);

        return true;
    }
}