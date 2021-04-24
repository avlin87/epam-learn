package com.liadov.cat.lesson9.exceptions;

/**
 * NoValueAnnotationException indicate that object do not have @Value Annotation
 *
 * @author Aleksandr Liadov on 4/20/2021
 */
public class NoValueAnnotationException extends RuntimeException {
    public NoValueAnnotationException(String s) {
        super(s);
    }
}
