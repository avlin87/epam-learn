package liadov.mypackage.lesson2;

public class CacheElement <T>{
    T element;
    int index;

    public CacheElement(T element, int index){
        this.element = element;
        this.index = index;
    }
}
