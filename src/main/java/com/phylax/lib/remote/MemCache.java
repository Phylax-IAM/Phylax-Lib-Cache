package com.phylax.lib.remote;

import java.util.Optional;
import java.util.concurrent.TimeoutException;
import net.rubyeye.xmemcached.MemcachedClient;
import redis.clients.jedis.json.JsonObjectMapper;
import net.rubyeye.xmemcached.exception.MemcachedException;

import com.phylax.lib.contract.RemoteCanCache;
import com.phylax.lib.exception.MemCacheException;
import com.phylax.lib.connector.MemCacheConnectionManager;

public class MemCache<K, V> implements RemoteCanCache<K, V> {

    private final MemcachedClient memcachedClient;

    private final JsonObjectMapper objectMapper;

    private final Class<V> type;

    private final int ttl;

    public MemCache(MemCacheConnectionManager memCacheConnectionManager, JsonObjectMapper objectMapper, Class<V> type, int ttl) {
        this.memcachedClient = memCacheConnectionManager.getConnection();
        this.objectMapper = objectMapper;
        this.type = type;
        this.ttl = ttl;
    }

    @Override
    public Optional<V> read(K key) {
        final String jsonKey = this.objectMapper.toJson(key);

        try {
            final String jsonValue = this.memcachedClient.get(jsonKey);
            return (jsonValue != null)
                    ? Optional.ofNullable(this.objectMapper.fromJson(jsonValue, this.type))
                    : Optional.empty();

        } catch (TimeoutException | MemcachedException | InterruptedException e) {
            throw new MemCacheException("Unable to read from MemCached");
        }
    }

    @Override
    public void write(K key, V value) {
        final String jsonKey = this.objectMapper.toJson(key);
        final String jsonValue = this.objectMapper.toJson(value);

        try {
            this.memcachedClient.set(jsonKey, ttl, jsonValue);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            throw new MemCacheException("Unable to write to MemCached");
        }
    }

    @Override
    public void delete(K key) {
        final String jsonKey = this.objectMapper.toJson(key);

        try {
            this.memcachedClient.delete(jsonKey);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            throw new MemCacheException("Unable to delete from MemCached");
        }
    }

    @Override
    public void clear() {

        try {
            this.memcachedClient.flushAll();
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            throw new RuntimeException(e);
        }
    }
}
