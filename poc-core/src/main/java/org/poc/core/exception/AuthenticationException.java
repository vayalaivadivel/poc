package org.poc.core.exception;

/**
 * Exception class for Authentication
 *
 * @author vadivel 12/15/2016
 */
public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 5796377234367925892L;

    public AuthenticationException(final String message) {
        super(message);
    }

    public AuthenticationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
