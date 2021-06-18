package com.epam.liadov.exception;

/**
 * NotFoundException - exception to be thrown when object was not found in database
 *
 * @author Aleksandr Liadov
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
