package com.phylax.lib.config;


/**
 * Configuration class for in-memory cache settings.
 *
 * <p>This class is immutable and holds the configuration for the cache's
 * capacity factor, which determines the portion of the JVM heap memory
 * allocated for the cache.</p>
 */
public final class InMemoryCacheConfig {

    /** Fraction of JVM maximum memory to use for the in-memory cache. */
    private final float capacityFactor;

    /**
     * Constructs a new {@code InMemoryCacheConfig} with the default capacity factor.
     *
     * <p>The default value is {@code 0.20}, meaning the cache will use up to
     * 20% of the JVM's maximum memory.</p>
     */
    public InMemoryCacheConfig() {
        this.capacityFactor = 0.20f;
    }

    /**
     * Constructs a new {@code InMemoryCacheConfig} with a custom capacity factor.
     *
     * @param capacityFactor the fraction of JVM maximum memory to allocate to the cache;
     *                       must be a positive value (typically between 0 and 1)
     */
    public InMemoryCacheConfig(float capacityFactor) {
        this.capacityFactor = capacityFactor;
    }

    /**
     * Returns the capacity factor of the cache.
     *
     * @return the fraction of JVM maximum memory allocated for the cache
     */
    public float getCapacityFactor() {
        return capacityFactor;
    }
}

