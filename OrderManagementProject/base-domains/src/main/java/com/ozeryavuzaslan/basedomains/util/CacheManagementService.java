package com.ozeryavuzaslan.basedomains.util;

public interface CacheManagementService {
    void clearStockCache();
    void clearCategoryCache();
    void clearStockCache(String cacheName);
}