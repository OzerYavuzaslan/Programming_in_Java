package com.ozeryavuzaslan.basedomains.util;

public interface CacheManagementService {
    void clearCache(String cacheName);
    boolean releaseCache(boolean isCacheRefresh, String cacheName);
}