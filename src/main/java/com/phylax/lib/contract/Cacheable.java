package com.phylax.lib.contract;

/**
 * Represents an object that can be cached and later retrieved in a specific type.
 * <p>
 * The {@code Cacheable} interface provides a contract for converting
 * the cached object into a desired target type. This is useful in
 * caching scenarios where the stored object may need to be accessed
 * or transformed into different representations at runtime.
 * </p>
 */
public interface Cacheable {

    /**
     * Returns the cached value represented as the specified type.
     * <p>
     * Implementations are expected to perform type conversion or casting
     * as needed. If the conversion is not possible, an exception may be
     * thrown (e.g., {@link ClassCastException}).
     * </p>
     *
     * @param <T>  the target type to cast or convert to
     * @param type the class object representing the target type
     * @return the cached value as an instance of the specified type
     * @throws ClassCastException if the object cannot be converted to the given type
     * @throws NullPointerException if {@code type} is {@code null}
     */
    <T> T as(Class<T> type);
}
