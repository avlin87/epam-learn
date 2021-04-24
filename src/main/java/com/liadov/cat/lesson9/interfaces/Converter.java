package com.liadov.cat.lesson9.interfaces;

/**
 * Converter - Functional interface for converting Types
 *
 * @author Aleksandr Liadov on 4/22/2021
 */
@FunctionalInterface
public interface Converter<F, T> {
    /**
     * Method converting value from current Class type (F) to target class type (T)
     *
     * @param from current Class of object
     * @return value converted to target Class
     */
    T convert(F from);
}


