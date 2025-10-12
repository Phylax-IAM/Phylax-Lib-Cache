package com.phylax.lib.connector;

import com.phylax.lib.config.PoolConfig;
import com.phylax.lib.config.RedisConfig;
import com.phylax.lib.contract.CanCacheManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisConnectionManager implements CanCacheManager<Jedis> {

    private final JedisPool jedisPool;

    public RedisConnectionManager(RedisConfig redisConfig) {
        final PoolConfig redisPoolConfig = redisConfig.getPoolConfig();

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(redisPoolConfig.getMaxConnections());
        poolConfig.setMaxIdle(redisPoolConfig.getMaxIdleConnections());
        poolConfig.setMinIdle(redisPoolConfig.getMinIdleConnections());
        poolConfig.setTestOnBorrow(redisPoolConfig.isTestOnBorrow());

        this.jedisPool = new JedisPool(
            poolConfig,
            redisConfig.getHost(),
            redisConfig.getPort(),
                (int) redisConfig.getTimeOut(),
                redisConfig.getCredentialConfig().getUserName(),
                redisConfig.getCredentialConfig().getPassword(),
                redisConfig.getUseSSL()
        );
    }

    @Override
    public Jedis getConnection() {
        return jedisPool.getResource();
    }
}
