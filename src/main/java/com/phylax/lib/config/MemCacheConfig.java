package com.phylax.lib.config;

/**
 * Immutable configuration class for connecting to a Memcached server.
 * <p>
 * This class encapsulates all necessary information to establish a connection
 * to a Memcached server, including host, port, operation timeout, and connection pool size.
 * All fields are {@code final}, making instances immutable and thread-safe.
 */
public final class MemCacheConfig {

    /** The Memcached server hostname or IP address. */
    private final String host;

    /** The Memcached server port number. */
    private final int port;

    /** Operation timeout in milliseconds for Memcached operations. */
    private final long timeOut;

    /** Number of connections to maintain in the connection pool. */
    private final int poolSize;

    private final int ttl;

    /**
     * Constructs a {@code MemCacheConfig} with default timeout and pool size.
     * <p>
     * Default timeout is 2000 milliseconds and default pool size is 1 connection.
     *
     * @param port the Memcached server port number
     * @param host the Memcached server hostname or IP address
     */
    public MemCacheConfig(int port, String host) {
        this.port = port;
        this.host = host;
        this.timeOut = 2000;
        this.poolSize = 1;
        this.ttl = 300000;
    }

    /**
     * Constructs a {@code MemCacheConfig} with custom timeout and pool size.
     *
     * @param host the Memcached server hostname or IP address
     * @param port the Memcached server port number
     * @param timeOut the operation timeout in milliseconds
     * @param poolSize the number of connections in the connection pool
     * @param ttl time to live in milliseconds
     */
    public MemCacheConfig(String host, int port, long timeOut, int poolSize, int ttl) {
        this.host = host;
        this.port = port;
        this.timeOut = timeOut;
        this.poolSize = poolSize;
        this.ttl = ttl;
    }

    /**
     * Returns the Memcached server hostname.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Returns the Memcached server port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns the operation timeout in milliseconds.
     *
     * @return the timeout
     */
    public long getTimeOut() {
        return timeOut;
    }

    /**
     * Returns the connection pool size.
     *
     * @return the pool size
     */
    public int getPoolSize() {
        return poolSize;
    }


    /**
     * Returns the time to live in milliseconds.
     *
     * @return the ttl
     */
    public int getTTL() {
        return ttl;
    }
}

