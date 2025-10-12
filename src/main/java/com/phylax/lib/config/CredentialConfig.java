package com.phylax.lib.config;

/**
 * Immutable configuration class that holds credentials for authentication.
 * <p>
 * This class encapsulates a username and password pair. Both fields are
 * {@code final}, making instances of this class immutable and thread-safe.
 */
public final class CredentialConfig {

    /** The username for authentication. */
    private final String userName;

    /** The password for authentication. */
    private final String password;

    public CredentialConfig() {
        this.userName = "user";
        this.password = "user";
    }

    /**
     * Constructs a {@code CredentialConfig} with the specified username and password.
     *
     * @param userName the username to use for authentication
     * @param password the password to use for authentication
     */
    public CredentialConfig(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Returns the username.
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}

