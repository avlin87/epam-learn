package com.epam.liadov.exception;

/**
 * NoContentException - exception to be thrown when object was not found in database
 *
 * @author Aleksandr Liadov
 */
public class NoContentException extends RuntimeException {
    public NoContentException(String message) {
        super(message);
    }
}
