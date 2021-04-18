package com.liadov.cat.lesson2;

import java.util.Objects;


/**
 * Container for cache element and index values.
 *
 * @param <T> type of element value to be stored in cache.
 * @author Aleksandr Liadov
 * @version 1.10 17 April 2020
 */
public class CacheElement<T> {

    private final T ELEMENT;
    private final int INDEX;

    public CacheElement(T element, int index) {
        this.ELEMENT = element;
        this.INDEX = index;
    }

    public T getELEMENT() {
        return ELEMENT;
    }

    public int getINDEX() {
        return INDEX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CacheElement)) return false;
        CacheElement<?> that = (CacheElement<?>) o;
        if (INDEX != that.getINDEX()) return false;
        return ELEMENT != null ? ELEMENT.equals(that.getELEMENT()) : that.getELEMENT() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ELEMENT, INDEX);
    }

    @Override
    public String toString() {
        return "CE{" +
                "e=" + ELEMENT +
                ", i=" + INDEX +
                '}';
    }
}
