package com.liadov.cat.lesson2;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Container for cache element and index values.
 *
 * @param <T> type of element value to be stored in cache.
 * @author Aleksandr Liadov
 */
@Getter
@EqualsAndHashCode
public class CacheElement<T> {

    private final T element;
    private final int index;

    public CacheElement(T element, int index) {
        this.element = element;
        this.index = index;
    }

    @Override
    public String toString() {
        return "CE{" +
                "e=" + element +
                ", i=" + index +
                '}';
    }
}
