package org.poc.core.exception;

/**
 *
 * Exception class for No data
 *
 * @author vadivel 12/14/2016
 */
public class NoDataFoundException extends RuntimeException {

    private static final long serialVersionUID = 6100133013084408935L;

    public NoDataFoundException(final String message) {
        super(message);
    }

    public NoDataFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
