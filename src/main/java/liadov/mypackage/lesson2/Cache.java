package liadov.mypackage.lesson2;

import liadov.mypackage.lesson4.MyCheckedException;
import liadov.mypackage.lesson4.ElementDoesNotExistException;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
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
        log.info(String.format("Cache [%d] with [%d] capacity created", this.hashCode(), capacity));
    }

    /**
     * Method for adding element to Cache if element is not null.
     *
     * @param element This element will be added to Cache if element is not null.
     * @param index   index of element that this element has in storage
     */
    public void add(T element, int index) {
        if (element != null) {
            log.info(String.format("Cache [%d]: element [%s]<%s> to [%d] index will be added", this.hashCode(), element, element.getClass().getName(), index));
            if (countElements < capacity) {
                cache[countElements++] = new CacheElement<>(element, index);
            } else {
                moveLeftCacheElements();
                cache[capacity - 1] = new CacheElement<>(element, index);
            }
        } else {
            log.info(String.format("Cache [%d]: element is NULL and will NOT be added", this.hashCode()));
        }
    }

    /**
     * Method for removing element from Cache
     *
     * @param element This element will be removed from Cache if present
     */
    public void delete(T element) {
        log.info(String.format("Cache [%d]: element [%s]<%s> to [%d] index will be added", this.hashCode(), element, element.getClass().getName(), getElementID(element)));
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
                log.debug(String.format("Cache [%d]: element [%s]<%s> is found", this.hashCode(), element, element.getClass().getName()));
                return true;
            }
        }
        log.debug(String.format("Cache [%d]: element [%s]<%s> NOT found", this.hashCode(), element, element.getClass().getName()));
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
                log.debug(String.format("Cache [%d]: element [%s]<%s> with index [%d] is found", this.hashCode(), cache[i].element, cache[i].element.getClass().getName(), index));
                return true;
            }
        }
        log.debug(String.format("Cache [%d]: element with index [%d] NOT found", this.hashCode(), index));
        return false;
    }

    /**
     * Method returns requested element from Cache
     *
     * @param index index of element that this element has in storage
     * @return if found return requested element else null
     */
    public T get(int index) throws MyCheckedException {
        if (isPresent(index)) {
            CacheElement<T> tempElement = getExistingCacheElementByIndex(index);
            moveLeftCacheElements(getElementID(index));
            cache[countElements - 1] = tempElement;
            log.debug(String.format("Cache [%d]: returned element [%s] with index [%d]", this.hashCode(), cache[countElements - 1].element, index));
            return cache[countElements - 1].element;
        }
        log.debug(String.format("Cache [%d]: returned NULL", this.hashCode()));
        return null;
    }

    /**
     * Method returns CacheElement from Cache by index
     *
     * @param index index of element that this element has in storage
     * @return if found return requested CacheElement
     * @throws MyCheckedException in case CacheElement was not found
     */
    private CacheElement<T> getExistingCacheElementByIndex(int index) throws MyCheckedException {
        for (int i = 0; i < cache.length; i++) {
            if (cache[i].index == index) {
                log.info(String.format("Cache [%d]: private method returned CacheElement %s", this.hashCode(), cache[i]));
                return cache[i];
            }
        }
        log.info(String.format("Cache [%d]: private method returned NULL", this.hashCode()));
        throw new MyCheckedException();
    }

    /**
     * Method clean Cache
     */
    public void clear() {
        for (int i = 0; i < cache.length; i++) {
            cache[i] = null;
        }
        countElements = 0;
        log.info(String.format("Cache [%d]: was cleared", this.hashCode()));
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
        log.debug(String.format("Cache [%d]: elements moved LEFT starting with [%d] index", this.hashCode(), startingIndex));
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
                log.debug(String.format("Cache [%d]: id of element [%s] identified with index=[%d]", this.hashCode(), element, i));
                return i;
            }
        }
        log.error(String.format("Cache [%d]: id of element [%s] unidentified", this.hashCode(), element));
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
                log.debug(String.format("Cache [%d]: element with index = [%d] identified with index = [%d] in cache", this.hashCode(), index, i));
                return i;
            }
        }
        log.error(String.format("Cache [%d]: element with index = [%d] unidentified", this.hashCode(), index));
        throw new ElementDoesNotExistException(index);
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