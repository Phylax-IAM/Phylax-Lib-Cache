package com.phylax.lib.config;

/**
 * Configuration class for managing connection pool settings.
 *
 * <p>This class is immutable and holds the configuration for the maximum number
 * of connections, as well as the minimum and maximum number of idle connections
 * that the pool should maintain.</p>
 */
public final class PoolConfig {

    /** Maximum number of connections allowed in the pool. */
    private final int maxConnections;

    /** Minimum number of idle connections to keep in the pool. */
    private final int minIdleConnections;

    /** Maximum number of idle connections allowed in the pool. */
    private final int maxIdleConnections;

    private final boolean testOnBorrow;

    public PoolConfig() {
        this.maxConnections = 10;
        this.minIdleConnections = 5;
        this.maxIdleConnections = 1;
        this.testOnBorrow = false;
    }

    /**
     * Constructs a new {@code PoolConfig} with the specified pool parameters.
     *
     * @param maxConnections the maximum number of connections in the pool
     * @param minIdleConnections the minimum number of idle connections to maintain
     * @param maxIdleConnections the maximum number of idle connections allowed
     * @param testOnBorrow this flag tells the pool to validate a connection before giving it to you (before “borrowing” it).
     */
    public PoolConfig(int maxConnections, int minIdleConnections, int maxIdleConnections, boolean testOnBorrow) {
        this.maxConnections = maxConnections;
        this.minIdleConnections = minIdleConnections;
        this.maxIdleConnections = maxIdleConnections;
        this.testOnBorrow = testOnBorrow;
    }

    /**
     * Returns the maximum number of connections allowed in the pool.
     *
     * @return the maximum number of connections
     */
    public int getMaxConnections() {
        return maxConnections;
    }

    /**
     * Returns the minimum number of idle connections to maintain in the pool.
     *
     * @return the minimum number of idle connections
     */
    public int getMinIdleConnections() {
        return minIdleConnections;
    }

    /**
     * Returns the maximum number of idle connections allowed in the pool.
     *
     * @return the maximum number of idle connections
     */
    public int getMaxIdleConnections() {
        return maxIdleConnections;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }
}
