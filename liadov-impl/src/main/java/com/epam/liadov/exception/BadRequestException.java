package com.epam.liadov.exception;

/**
 * BadRequestException - class for catching RuntimeException
 *
 * @author Aleksandr Liadov
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
