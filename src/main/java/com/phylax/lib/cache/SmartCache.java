package com.phylax.lib.cache;

import com.phylax.lib.contract.CanCacheService;
import com.phylax.lib.contract.LocalCanCache;
import com.phylax.lib.contract.RemoteCanCache;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class SmartCache<K, V> implements CanCacheService<K, V> {

    private final LocalCanCache<K, V> localCache;

    private final RemoteCanCache<K, V> remoteCache;

    public SmartCache(LocalCanCache<K, V> localCache, RemoteCanCache<K, V> remoteCache) {
        this.localCache = localCache;
        this.remoteCache = remoteCache;
    }

    @Override
    public Optional<V> read(K key) {
        final Optional<V> localData = this.localCache.read(key);
        return localData.isEmpty()
                ? this.remoteCache.read(key)
                : localData;
    }

    @Override
    public void write(K key, V value) {
        CompletableFuture.runAsync(() -> this.remoteCache.write(key, value));
        CompletableFuture.runAsync(() -> this.localCache.write(key, value));
    }

    @Override
    public void delete(K key) {
        CompletableFuture.runAsync(() -> this.remoteCache.delete(key));
        CompletableFuture.runAsync(() -> this.localCache.delete(key));
    }

    @Override
    public void clear() {
        CompletableFuture.runAsync(this.remoteCache::clear);
        CompletableFuture.runAsync(this.localCache::clear);
    }
}
