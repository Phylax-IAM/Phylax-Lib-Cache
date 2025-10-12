package com.phylax.lib.connector;

import com.phylax.lib.collection.LRUCache;
import com.phylax.lib.config.InMemoryCacheConfig;
import com.phylax.lib.contract.CanCacheManager;

public class InMemoryCacheConnectionManager<K, V> implements CanCacheManager<LRUCache<K, V>> {

    private final LRUCache<K, V> localCache;

    public InMemoryCacheConnectionManager(InMemoryCacheConfig config) {
        this.localCache = new LRUCache<>(config.getCapacityFactor());
    }

    @Override
    public LRUCache<K, V> getConnection() {
        return this.localCache;
    }
}
