package com.ozeryavuzaslan.stockservice.service;

public interface CacheManagementService {
    void clearCache();
    void clearCache(String cacheName);
}
