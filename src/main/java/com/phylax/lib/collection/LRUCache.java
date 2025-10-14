package com.phylax.lib.collection;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * A thread-safe Least Recently Used (LRU) cache implementation.
 * <p>
 * This cache maintains a fixed maximum capacity. When the number of entries exceeds
 * {@link #maxCapacity}, the least recently used entry is automatically evicted.
 * <p>
 * Accessing or inserting an entry updates its usage order, ensuring that frequently
 * accessed entries remain in the cache while older, less used entries are evicted first.
 * </p>
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
public class LRUCache<K, V> {

    /** Maximum number of entries the cache can hold. */
    private final long maxCapacity;

    /** Internal storage for cache entries. */
    private final ConcurrentHashMap<K, V> cacheMap = new ConcurrentHashMap<>();

    /** Queue to track access order for LRU eviction. */
    private final LinkedBlockingDeque<K> accessOrderQueue = new LinkedBlockingDeque<>();

    /**
     * Constructs an LRUCache using a default capacity factor.
     * <p>
     * The maximum cache capacity is set to 20% of the JVM's maximum available memory.
     * This constructor is useful when no custom capacity factor is specified.
     */
    public LRUCache() {
        this.maxCapacity = (long) (0.20f * Runtime.getRuntime().maxMemory());
    }

    /**
     * Constructs an LRUCache with a custom capacity factor.
     * <p>
     * The capacity factor determines the fraction of the JVM's maximum memory
     * that can be used by the cache.
     * <ul>
     *     <li>If {@code capacityFactor} is less than 0.20, it defaults to 0.20 (20%).</li>
     *     <li>If {@code capacityFactor} is greater than 0.50, it defaults to 0.50 (50%).</li>
     * </ul>
     * The maximum cache capacity is then calculated as:
     * <pre>{@code maxCapacity = capacityFactor * Runtime.getRuntime().maxMemory()}</pre>
     *
     * @param capacityFactor the desired fraction of max JVM memory to use for the cache
     */
    public LRUCache(float capacityFactor) {

        if(capacityFactor < 0.20f) {
            capacityFactor = 0.20f;
        } else if(capacityFactor > 0.50f) {
            capacityFactor = 0.50f;
        }
        this.maxCapacity = (long) (capacityFactor * Runtime.getRuntime().maxMemory());
    }

    /**
     * Removes the eldest entries if the cache exceeds its maximum capacity.
     * <p>
     * This method is called after every insertion to maintain the LRU property.
     * </p>
     */
    private void verifyAndEvict() {
        while (this.accessOrderQueue.size() > this.maxCapacity) {
            K eldest = this.accessOrderQueue.pollFirst();
            if (eldest != null) {
                this.cacheMap.remove(eldest);
            }
        }
    }

    /**
     * Updates the access order of the given key.
     * <p>
     * If the key exists, it is moved to the end of the access order queue to mark it
     * as recently used.
     * </p>
     *
     * @param key the key whose access order should be updated
     */
    private void reorder(K key) {

        if(this.accessOrderQueue.remove(key)) {
            this.accessOrderQueue.addLast(key);
        }
    }

    /**
     * Inserts or updates the value for the given key in the cache.
     * <p>
     * This method updates the access order of the key and evicts the least recently
     * used entry if the cache exceeds its maximum capacity.
     * </p>
     *
     * @param key   the key to insert or update
     * @param value the value associated with the key
     */
    public synchronized void put(K key, V value) {
        this.reorder(key);
        this.cacheMap.put(key, value);
        this.verifyAndEvict();
    }

    /**
     * Retrieves the value associated with the given key, updating its usage order.
     * <p>
     * If the key exists in the cache, it is marked as recently used.
     * Returns {@code null} if the key is not present.
     * </p>
     *
     * @param key the key whose value is to be retrieved
     * @return the value associated with the key, or {@code null} if not present
     */
    public synchronized V get(K key) {

        if (this.cacheMap.containsKey(key)) {
            this.reorder(key);
            return this.cacheMap.get(key);
        }
        return null;
    }

    /**
     * Retrieves the value associated with the given key, or returns a default value
     * if the key is not present.
     * <p>
     * This method does not update the usage order.
     * </p>
     *
     * @param key          the key whose value is to be retrieved
     * @param defaultValue the default value to return if the key is not present
     * @return the value associated with the key, or {@code defaultValue} if not present
     */
    public synchronized V getOrDefault(K key, V defaultValue) {
        return this.cacheMap.getOrDefault(key, defaultValue);
    }

    /**
     * Removes the entry associated with the given key from the cache.
     * <p>
     * This method is synchronized to ensure thread-safety when multiple threads
     * attempt to modify the cache concurrently.
     * If the key exists, it is removed from both the access order queue and the cache map.
     * </p>
     *
     * @param key the key of the entry to remove; must not be {@code null}
     * @throws NullPointerException if the key is {@code null}
     */
    public synchronized void delete(K key) {

        if(this.cacheMap.containsKey(key)) {
            this.accessOrderQueue.remove(key);
            this.cacheMap.remove(key);
        }
    }

    /**
     * Clears the cache completely.
     * <p>
     * This method removes all entries from the internal cache map as well as
     * the access order queue. After calling this method, the cache will be empty.
     * <p>
     * This method is synchronized to ensure thread-safety when clearing the cache
     * while other threads might be accessing or modifying it.
     */
    public synchronized void clear() {
        this.cacheMap.clear();
        this.accessOrderQueue.clear();
    }
}
