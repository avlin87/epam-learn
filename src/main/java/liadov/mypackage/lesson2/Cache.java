package liadov.mypackage.lesson2;

import java.util.Arrays;

public class Cache<T> {
    private CacheElement<T>[] cache;
    private int capacity;
    private int countElements = 0;

    /**
     * Cache constructor with initialization of cache according to received capacity
     *
     * @param capacity
     */
    public Cache(int capacity) {
        this.capacity = capacity;
        cache = new CacheElement[capacity];
    }

    /**
     * Method for adding element to Cache if element is not null.
     *
     * @param element This element will be added to Cache if element is not null.
     * @param index   index of element that this element has in storage
     */
    public void add(T element, int index) {
        if (element != null) {
            if (countElements < capacity) {
                cache[countElements++] = new CacheElement<>(element, index);
            } else {
                moveLeftCacheElements();
                cache[capacity - 1] = new CacheElement<>(element, index);
            }
        }
    }

    /**
     * Method for removing element from Cache
     *
     * @param element This element will be removed from Cache if present
     */
    public void delete(T element) {
        if (isPresent(element)) {
            moveLeftCacheElements(getElementID(element));
            cache[--countElements] = null;
        }
    }

    /**
     * Method for checking whether element is present in Cache by element
     *
     * @param element the presence of this element is checked
     * @return boolean
     */
    public boolean isPresent(T element) {
        for (int i = 0; i < cache.length; i++) {
            if (cache[i] != null && cache[i].element.equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method for checking whether element is present in Cache by index
     *
     * @param index index of element to be found in Cache
     * @return boolean
     */
    public boolean isPresent(int index) {
        for (int i = 0; i < cache.length; i++) {
            if (cache[i] != null && cache[i].index == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method returns requested element from Cache
     *
     * @param index index of element that this element has in storage
     * @return if found return requested element else null
     */
    public T get(int index) {
        if (isPresent(index)) {
            CacheElement<T> tempElement = getExistingCacheElementByIndex(index);
            moveLeftCacheElements(getElementID(index));
            cache[countElements - 1] = tempElement;
            return cache[countElements - 1].element;
        }
        return null;
    }

    /**
     * Method returns CacheElement from Cache by index
     *
     * @param index index of element that this element has in storage
     * @return if found return requested CacheElement else null
     */
    private CacheElement<T> getExistingCacheElementByIndex(int index) {
        for (int i = 0; i < cache.length; i++) {
            if (cache[i].index == index) {
                return cache[i];
            }
        }
        return null;
    }

    /**
     * Method clean Cache
     */
    public void clear() {
        for (int i = 0; i < cache.length; i++) {
            cache[i] = null;
        }
        countElements = 0;
    }

    /**
     * Method moves to the left Cache elements
     * Elements move to the left starts from first value of idBeginMove if Method receives idBeginMove
     * else move to the left all of elements if Method receives blank idBeginMove
     *
     * @param idBeginMove can be specified with starting index to start move. All element will be moved to the left in case of blank value
     */
    private void moveLeftCacheElements(int... idBeginMove) {
        int startingIndex = 0;
        if (idBeginMove.length > 0) {
            startingIndex = idBeginMove[0];
        }
        for (int i = startingIndex; i < cache.length - 1; i++) {
            cache[i] = cache[i + 1];
        }
    }

    /**
     * Method finds id of element in Cache by element
     *
     * @param element this element id will be found
     * @return int
     */
    private int getElementID(T element) {
        for (int i = 0; i < cache.length; i++) {
            if (cache[i].element.equals(element)) {
                return i;
            }
        }
        return capacity - 1;
    }

    /**
     * Method finds id of element in Cache by index
     *
     * @param index index of element that this element has in storage
     * @return int
     */
    private int getElementID(int index) {
        for (int i = 0; i < cache.length; i++) {
            if (cache[i].index == index) {
                return i;
            }
        }
        return capacity - 1;
    }

    @Override
    public String toString() {
        return "Cache{" +
                "cache=" + Arrays.toString(cache) +
                ", cacheCapacity=" + capacity +
                ", countCacheElements=" + countElements +
                '}';
    }
}