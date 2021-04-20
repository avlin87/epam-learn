package com.liadov.cat.lesson2;

import com.liadov.cat.exceptions.IllegalStateOfElement;
import com.liadov.cat.exceptions.ElementNotFoundByIndex;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.UUID;


/**
 * Cache is a storage that collects temporary data to apps load faster.
 * A cache makes it easy to quickly retrieve data, which in turn helps devices run faster
 *
 * @param <T> type of values to be stored in Cache
 * @author Aleksandr Liadov
 */
@Slf4j
public class Cache<T> {

    private final String uniqId;
    private final CacheElement<T>[] cacheElements;
    private final int capacity;

    private int countElements = 0;

    /**
     * Cache constructor with initialization of size
     *
     * @param capacity int cache size
     */
    @SuppressWarnings("unchecked")
    public Cache(int capacity) {
        this.uniqId = generateUniqId();
        this.capacity = capacity;
        this.cacheElements = new CacheElement[capacity];
        log.debug(toString());
    }

    /**
     * Method add element to Cache if element is not null.
     *
     * @param element element to be added to Cache.
     * @param index   index of element to be added to Cache.
     */
    public void add(T element, int index) {
        if (element == null) {
            log.error("[{}}]: provided element is NULL", this.uniqId);
            throw new IllegalStateOfElement("Element is null");
        }

        int cacheIndex;
        if (countElements < capacity) {
            cacheIndex = countElements++;
        } else {
            moveLeftCacheElements();
            cacheIndex = capacity - 1;
        }
        cacheElements[cacheIndex] = new CacheElement<>(element, index);
        log.info("[{}]: element [{}]<{}> with [{}] index added", this.uniqId, element, element.getClass().getName(), index);
    }

    /**
     * Method remove element from Cache
     *
     * @param element This element to be removed from Cache if present
     */
    public void delete(@NonNull T element) {
        if (!isPresent(element)) {
            log.error("[{}]: element [{}]<{}> was not found for removal", this.uniqId, element, element.getClass().getName());
            throw new IllegalStateOfElement("Element not present in cache");
        }
        log.info("[{}]: element [{}]<{}> to [{}] index will be removed", this.uniqId, element, element.getClass().getName(), getElementPositionByElement(element));
        moveLeftCacheElements(getElementPositionByElement(element));
        cacheElements[--countElements] = null;
    }

    /**
     * Method checks whether element is present in Cache by element
     *
     * @param element presence of this element is checked
     * @return boolean
     */
    public boolean isPresent(@NonNull T element) {
        for (CacheElement<T> cacheElement : cacheElements) {
            if (cacheElement != null && cacheElement.getElement().equals(element)) {
                log.debug("[{}]: element [{}]<{}> is found", this.uniqId, element, element.getClass().getName());
                return true;
            }
        }
        log.debug("[{}]: element [{}]<{}> NOT found", this.uniqId, element, element.getClass().getName());
        return false;
    }

    /**
     * Method checks whether element is present in Cache by index
     *
     * @param index index of element to be found in Cache
     * @return boolean
     */
    public boolean isPresent(int index) {
        for (CacheElement<T> cacheElement : cacheElements) {
            if (cacheElement != null && cacheElement.getIndex() == index) {
                log.debug("[{}]: element [{}]<{}> with index [{}] is found", this.uniqId, cacheElement.getElement(), cacheElement.getElement().getClass().getName(), index);
                return true;
            }
        }
        log.debug("[{}]: element with index [{}] NOT found", this.uniqId, index);
        return false;
    }

    /**
     * Method returns requested element from Cache
     *
     * @param index index of element in Storage
     * @return return requested element if found
     */
    public T get(int index) {
        if (!isPresent(index)) {
            log.error("Element not found");
            throw new ElementNotFoundByIndex(index);
        }
        int lastCachePosition = countElements - 1;
        moveCacheElementToLastPosition(index, lastCachePosition);
        log.debug("[{}]: element [{}] returned", this.uniqId, cacheElements[lastCachePosition].getElement());
        return cacheElements[lastCachePosition].getElement();
    }

    /**
     * Method set all Cache elements to null
     */
    public void clear() {
        Arrays.fill(cacheElements, null);
        countElements = 0;
        log.info("[{}]: cleared", this.uniqId);
    }

    @Override
    public String toString() {
        return "[" + this.uniqId + "]: {" +
                "cacheCapacity=" + capacity +
                ", countCacheElements=" + countElements +
                ", cache=" + Arrays.toString(cacheElements) +
                '}';
    }

    private String generateUniqId() {
        return "Cache-" + UUID.randomUUID().toString();
    }

    private void moveLeftCacheElements(int... proposedStartMovePosition) {
        int startMovePosition = 0;
        if (proposedStartMovePosition.length > 0) {
            startMovePosition = proposedStartMovePosition[0];
        }
        log.debug("[{}]: elements moved LEFT starting with [{}] index", this.uniqId, startMovePosition);
        for (int position = startMovePosition; position < cacheElements.length - 1; position++) {
            cacheElements[position] = cacheElements[position + 1];
        }
    }

    private int getElementPositionByElement(T element) throws ElementNotFoundByIndex {
        for (int position = 0; position < cacheElements.length; position++) {
            if (cacheElements[position].getElement().equals(element)) {
                log.debug("[{}]: element [{}] identified with position = [{}] in cache", this.uniqId, element, position);
                return position;
            }
        }
        log.error("requested element is not found");
        throw new IllegalStateOfElement("Element not present in cache");
    }

    private void moveCacheElementToLastPosition(int index, int lastCachePosition) {
        CacheElement<T> tempElement = getCacheElementByIndex(index);
        moveLeftCacheElements(getElementPositionByIndex(index));
        cacheElements[lastCachePosition] = tempElement;
        log.debug("[{}]: element [{}] moved to last position = [{}]", this.uniqId, tempElement.getElement(), lastCachePosition);
    }

    private CacheElement<T> getCacheElementByIndex(int index) throws ElementNotFoundByIndex {
        for (CacheElement<T> cacheElement : cacheElements) {
            if (cacheElement.getIndex() == index) {
                log.debug("[{}]: method returned CacheElement {}", this.uniqId, cacheElement);
                return cacheElement;
            }
        }
        log.error("requested element is not found");
        throw new ElementNotFoundByIndex(index);
    }

    private int getElementPositionByIndex(int index) throws ElementNotFoundByIndex {
        for (int position = 0; position < cacheElements.length; position++) {
            if (cacheElements[position].getIndex() == index) {
                log.debug("[{}]: element [{}] identified with position = [{}] in cache", this.uniqId, cacheElements[position].getElement(), position);
                return position;
            }
        }
        log.error("requested element is not found");
        throw new ElementNotFoundByIndex(index);
    }

}