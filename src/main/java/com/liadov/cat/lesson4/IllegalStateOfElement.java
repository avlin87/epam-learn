package com.liadov.cat.lesson4;

/**
 * Signals that an attempt to access Element failed
 */
public class IllegalStateOfElement extends RuntimeException {

    public IllegalStateOfElement(String message) {
        super(message);
    }

}
