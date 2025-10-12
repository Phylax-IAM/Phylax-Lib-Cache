package com.phylax.lib.local;

import com.phylax.lib.contract.CanCache;
import com.phylax.lib.collection.LRUCache;
import com.phylax.lib.contract.LocalCanCache;
import com.phylax.lib.connector.InMemoryCacheConnectionManager;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of the {@link CanCache} interface backed by a
 * thread-safe {@link ConcurrentHashMap}.
 * <p>
 * This implementation is useful for testing, prototyping, or lightweight use cases
 * where persistence across application restarts is not required.
 * </p>
 *
 * @param <K> the type of the key used to identify a persisted value
 * @param <V> the type of the value being persisted
 */
public final class InMemoryCache<K, V> implements LocalCanCache<K, V> {

    /**
     * Internal cache for storing key-value pairs in memory.
     */
    private final LRUCache<K, V> lruCache;


    public InMemoryCache(InMemoryCacheConnectionManager<K, V> inMemoryCacheConnectionManager) {
        this.lruCache = inMemoryCacheConnectionManager.getConnection();
    }

    /**
     * Retrieves the value associated with the given key from memory.
     *
     * @param key the identifier of the value to retrieve; must not be {@code null}
     * @return an {@link Optional} containing the value if present,
     *         or {@link Optional#empty()} if no value is mapped to the key
     */
    @Override
    public Optional<V> read(K key) {
        return Optional.ofNullable(this.lruCache.getOrDefault(key, null));
    }

    /**
     * Persists the given value in memory under the specified key.
     * <p>
     * If the key already exists, its value will be overwritten.
     * </p>
     *
     * @param key   the identifier under which to persist the value; must not be {@code null}
     * @param value the value to persist; must not be {@code null}
     */
    @Override
    public void write(K key, V value) {
        this.lruCache.put(key, value);
    }

    /**
     * Removes the value associated with the specified key from memory.
     * <p>
     * If the key does not exist, this method has no effect.
     * This method delegates the removal to the underlying {@link LRUCache}.
     * </p>
     *
     * @param key the key of the entry to remove; must not be {@code null}
     */
    @Override
    public void delete(K key) {
        this.lruCache.delete(key);
    }

    /**
     * Removes all entries from the in-memory cache.
     * <p>
     * This method delegates the clear operation to the underlying {@link LRUCache}
     * instance, effectively resetting the cache to an empty state.
     * After this call, all previously cached data will be lost.
     */
    @Override
    public void clear() {
        this.lruCache.clear();
    }
}
