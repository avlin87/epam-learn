package liadov.mypackage.lesson2;

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
        return index == that.index && element.equals(that.element);
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
