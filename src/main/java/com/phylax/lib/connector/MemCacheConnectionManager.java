package com.phylax.lib.connector;

import com.phylax.lib.config.MemCacheConfig;
import com.phylax.lib.contract.CanCacheManager;
import com.phylax.lib.exception.MemCacheException;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;

import java.io.IOException;

public class MemCacheConnectionManager implements CanCacheManager<MemcachedClient> {

    private MemcachedClient client = null;

    public MemCacheConnectionManager(MemCacheConfig config) throws IOException {

        try {
            final MemcachedClientBuilder builder = new XMemcachedClientBuilder(String.format("%s:%s", config.getHost(), config.getPort()));
            builder.setConnectionPoolSize(config.getPoolSize());
            builder.setOpTimeout(config.getTimeOut());
            this.client = builder.build();

        } catch(IOException e) {
            throw new MemCacheException("Unable to connect to Mem");
        } finally {

            if(this.client != null) {

                try {
                    this.client.shutdown();
                } catch (IOException e) {
                    throw new MemCacheException(e);
                }
            }
        }
    }

    @Override
    public MemcachedClient getConnection() {
        return this.client;
    }
}
