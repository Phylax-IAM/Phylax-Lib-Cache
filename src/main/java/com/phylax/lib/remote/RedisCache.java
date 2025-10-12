package com.phylax.lib.remote;

import java.util.Optional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.json.JsonObjectMapper;

import com.phylax.lib.contract.RemoteCanCache;
import com.phylax.lib.exception.RedisException;
import com.phylax.lib.connector.RedisConnectionManager;

public final class RedisCache<K, V> implements RemoteCanCache<K, V> {

    private final RedisConnectionManager redisConnectionManager;

    private final JsonObjectMapper objectMapper;

    private final Class<V> type;

    public RedisCache(RedisConnectionManager redisConnectionManager, JsonObjectMapper objectMapper, Class<V> type) {
        this.redisConnectionManager = redisConnectionManager;
        this.objectMapper = objectMapper;
        this.type = type;
    }

    @Override
    public Optional<V> read(K key) {

        try(Jedis jedis = redisConnectionManager.getConnection()) {
            final String jsonKey = objectMapper.toJson(key);
            final String jsonValue = jedis.get(jsonKey);
            return (jsonValue != null) ? Optional.ofNullable(objectMapper.fromJson(jsonValue, type)) : Optional.empty();

        } catch (Exception e) {
            throw new RedisException("Unable to read from redis");
        }
    }

    @Override
    public void write(K key, V value) {

        try(Jedis jedis = redisConnectionManager.getConnection()) {
            final String jsonKey = objectMapper.toJson(key);
            final String jsonValue = objectMapper.toJson(value);
            jedis.set(jsonKey, jsonValue);

        } catch (Exception e) {
            throw new RedisException("Unable to write to redis");
        }
    }

    @Override
    public void delete(K key) {

        try(Jedis jedis = redisConnectionManager.getConnection()) {
            final String jsonKey = objectMapper.toJson(key);
            jedis.del(jsonKey);

        } catch (Exception e) {
            throw new RedisException("Unable to delete from redis");
        }
    }

    @Override
    public void clear() {

        try(Jedis jedis = redisConnectionManager.getConnection()) {
            jedis.flushDB();

        } catch (Exception e) {
            throw new RedisException("Unable to flush all data from redis");
        }
    }
}
