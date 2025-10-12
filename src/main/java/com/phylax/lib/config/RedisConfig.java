package com.phylax.lib.config;

/**
 * Immutable configuration class for Redis connection settings.
 * <p>
 * This class encapsulates all necessary information to connect to a Redis server,
 * including host, port, timeout, database index, connection pool configuration,
 * and credentials. All fields are {@code final}, making instances immutable and thread-safe.
 */
public final class RedisConfig {

    /** The Redis server hostname or IP address. */
    private final String host;

    /** The Redis server port number. */
    private final int port;

    /** The Redis server to use SSL or not. */
    private final boolean useSSL;

    /** Connection timeout in milliseconds. */
    private final long timeOut;

    /** The Redis database index to use (default is usually 0). */
    private final int dataBaseIndex;

    /** Configuration for the connection pool. */
    private final PoolConfig poolConfig;

    /** Credentials for authenticating with the Redis server. */
    private final CredentialConfig credentialConfig;

    /**
     * Constructs a new {@code RedisConfig} with the specified host and port,
     * using default values for other configuration options.
     *
     * <p>Default values:</p>
     * <ul>
     *     <li>{@code timeOut} = 2000 milliseconds</li>
     *     <li>{@code dataBaseIndex} = 0</li>
     *     <li>{@code poolConfig} = {@code null}</li>
     *     <li>{@code credentialConfig} = {@code null}</li>
     * </ul>
     *
     * @param host the Redis server hostname or IP address
     * @param port the Redis server port
     */
    public RedisConfig(String host, int port, PoolConfig poolConfig, CredentialConfig credentialConfig) {
        this.host = host;
        this.port = port;
        this.useSSL = false;
        this.timeOut = 2000;
        this.dataBaseIndex = 0;
        this.poolConfig = poolConfig;
        this.credentialConfig = credentialConfig;
    }

    /**
     * Constructs a {@code RedisConfig} with the specified parameters.
     *
     * @param host the Redis server hostname or IP address
     * @param port the Redis server port number
     * @param useSSL the Redis server use SSL or TLS
     * @param timeOut the connection timeout in milliseconds
     * @param dataBaseIndex the Redis database index to connect to
     * @param poolConfig the connection pool configuration
     * @param credentialConfig the credentials for authentication
     */
    public RedisConfig(String host, int port, boolean useSSL, long timeOut, int dataBaseIndex, PoolConfig poolConfig, CredentialConfig credentialConfig) {
        this.host = host;
        this.port = port;
        this.useSSL = useSSL;
        this.timeOut = timeOut;
        this.dataBaseIndex = dataBaseIndex;
        this.poolConfig = poolConfig;
        this.credentialConfig = credentialConfig;
    }

    /**
     * Returns the Redis server host.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Returns the Redis server port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns whether SSL/TLS is enabled for the Redis connection.
     *
     * <p>If {@code true}, the client will connect to the Redis server over a secure
     * SSL/TLS connection. If {@code false}, the connection will be unencrypted.</p>
     *
     * @return {@code true} if SSL/TLS is enabled; {@code false} otherwise
     */
    public boolean getUseSSL() {
        return useSSL;
    }

    /**
     * Returns the connection timeout in milliseconds.
     *
     * @return the timeout
     */
    public long getTimeOut() {
        return timeOut;
    }

    /**
     * Returns the Redis database index.
     *
     * @return the database index
     */
    public int getDataBaseIndex() {
        return dataBaseIndex;
    }

    /**
     * Returns the connection pool configuration.
     *
     * @return the pool configuration
     */
    public PoolConfig getPoolConfig() {
        return poolConfig;
    }

    /**
     * Returns the credentials used for authentication with Redis.
     *
     * @return the credential configuration
     */
    public CredentialConfig getCredentialConfig() {
        return credentialConfig;
    }
}