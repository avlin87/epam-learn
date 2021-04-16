package com.liadov.cat.lesson2;

import java.util.Objects;

public class CacheElement <T>{
    T element;
    int index;

    public CacheElement(T element, int index){
        this.element = element;
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CacheElement)) return false;
        CacheElement<?> that = (CacheElement<?>) o;
        if (index != that.index) return false;
        return element != null ? element.equals(that.element) : that.element == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, index);
    }

    @Override
    public String toString() {
        return "CE{" +
                "e=" + element +
                ", i=" + index +
                '}';
    }
}
