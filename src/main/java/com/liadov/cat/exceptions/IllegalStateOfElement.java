package com.liadov.cat.exceptions;

/**
 * Signals that an attempt to access Element failed
 *
 * @author Aleksandr Liadov
 */
public class IllegalStateOfElement extends RuntimeException {

    public IllegalStateOfElement(String message) {
        super(message);
    }

}
