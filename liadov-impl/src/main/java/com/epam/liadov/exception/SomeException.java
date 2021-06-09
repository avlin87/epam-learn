package com.epam.liadov.exception;

/**
 * SomeException - class for catching RuntimeException
 *
 * @author Aleksandr Liadov
 */
public class SomeException extends RuntimeException {
    public SomeException(String message) {
        super(message);
    }
}
