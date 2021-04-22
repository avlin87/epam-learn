package com.liadov.cat.lesson9.interfaces;

/**
 * Converter - Functional interface for converting Types
 *
 * @author Aleksandr Liadov on 4/22/2021
 */

@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);
}


