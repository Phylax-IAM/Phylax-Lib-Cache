package com.phylax.lib.exception;

public class MemCacheException extends RuntimeException {

    public MemCacheException(Throwable cause) {
        super(cause);
    }

    public MemCacheException(String message) {
        super(message);
    }

}
