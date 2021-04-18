package com.liadov.cat.lesson2;

import com.liadov.cat.lesson4.IllegalStateOfElement;
import com.liadov.cat.lesson4.ElementNotFoundByIndex;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.UUID;


/**
 * Cache is a storage that collects temporary data to apps load faster.
 * A cache makes it easy to quickly retrieve data, which in turn helps devices run faster
 *
 * @param <T> type of values to be stored in Cache
 * @author Aleksandr Liadov
 * @version 1.10 17 April 2020
 */
@Slf4j
public class Cache<T> {

    private final String UNIQ_ID;
    private final CacheElement<T>[] CACHE_ELEMENTS;
    private final int CAPACITY;

    private int countElements = 0;

    /**
     * Cache constructor with initialization of size
     *
     * @param capacity int cache size
     */
    @SuppressWarnings("unchecked")
    public Cache(int capacity) {
        this.UNIQ_ID = generateUniqId();
        this.CAPACITY = capacity;
        this.CACHE_ELEMENTS = new CacheElement[capacity];
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
            log.error("[{}}]: provided element is NULL", this.UNIQ_ID);
            throw new IllegalStateOfElement("Element is null");
        }

        int cacheIndex;
        if (countElements < CAPACITY) {
            cacheIndex = countElements++;
        } else {
            moveLeftCacheElements();
            cacheIndex = CAPACITY - 1;
        }
        CACHE_ELEMENTS[cacheIndex] = new CacheElement<>(element, index);
        log.info("[{}]: element [{}]<{}> with [{}] index added", this.UNIQ_ID, element, element.getClass().getName(), index);
    }

    /**
     * Method remove element from Cache
     *
     * @param element This element to be removed from Cache if present
     */
    public void delete(T element) {
        if (!isPresent(element)) {
            log.error("[{}]: element [{}]<{}> was not found for removal", this.UNIQ_ID, element, element.getClass().getName());
            throw new IllegalStateOfElement("Element not present in cache");
        }
        log.info("[{}]: element [{}]<{}> to [{}] index will be removed", this.UNIQ_ID, element, element.getClass().getName(), getElementPositionByElement(element));
        moveLeftCacheElements(getElementPositionByElement(element));
        CACHE_ELEMENTS[--countElements] = null;
    }

    /**
     * Method checks whether element is present in Cache by element
     *
     * @param element presence of this element is checked
     * @return boolean
     */
    public boolean isPresent(T element) {
        for (CacheElement<T> cacheElement : CACHE_ELEMENTS) {
            if (cacheElement != null && cacheElement.getELEMENT().equals(element)) {
                log.debug("[{}]: element [{}]<{}> is found", this.UNIQ_ID, element, element.getClass().getName());
                return true;
            }
        }
        log.debug("[{}]: element [{}]<{}> NOT found", this.UNIQ_ID, element, element.getClass().getName());
        return false;
    }

    /**
     * Method checks whether element is present in Cache by index
     *
     * @param index index of element to be found in Cache
     * @return boolean
     */
    public boolean isPresent(int index) {
        for (CacheElement<T> cacheElement : CACHE_ELEMENTS) {
            if (cacheElement != null && cacheElement.getINDEX() == index) {
                log.debug("[{}]: element [{}]<{}> with index [{}] is found", this.UNIQ_ID, cacheElement.getELEMENT(), cacheElement.getELEMENT().getClass().getName(), index);
                return true;
            }
        }
        log.debug("[{}]: element with index [{}] NOT found", this.UNIQ_ID, index);
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
        log.debug("[{}]: element [{}] returned", this.UNIQ_ID, CACHE_ELEMENTS[lastCachePosition].getELEMENT());
        return CACHE_ELEMENTS[lastCachePosition].getELEMENT();
    }

    /**
     * Method set all Cache elements to null
     */
    public void clear() {
        Arrays.fill(CACHE_ELEMENTS, null);
        countElements = 0;
        log.info("[{}]: was cleared", this.UNIQ_ID);
    }

    @Override
    public String toString() {
        return "[" + this.UNIQ_ID + "]: {" +
                "cacheCapacity=" + CAPACITY +
                ", countCacheElements=" + countElements +
                ", cache=" + Arrays.toString(CACHE_ELEMENTS) +
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
        log.debug("[{}]: elements moved LEFT starting with [{}] index", this.UNIQ_ID, startMovePosition);
        for (int position = startMovePosition; position < CACHE_ELEMENTS.length - 1; position++) {
            CACHE_ELEMENTS[position] = CACHE_ELEMENTS[position + 1];
        }
    }

    private int getElementPositionByElement(T element) throws ElementNotFoundByIndex {
        for (int position = 0; position < CACHE_ELEMENTS.length; position++) {
            if (CACHE_ELEMENTS[position].getELEMENT().equals(element)) {
                log.debug("[{}]: element [{}] identified with position = [{}] in cache", this.UNIQ_ID, element, position);
                return position;
            }
        }
        log.error("requested element is not found");
        throw new IllegalStateOfElement("Element not present in cache");
    }

    private void moveCacheElementToLastPosition(int index, int lastCachePosition) {
        CacheElement<T> tempElement = getCacheElementByIndex(index);
        moveLeftCacheElements(getElementPositionByIndex(index));
        CACHE_ELEMENTS[lastCachePosition] = tempElement;
        log.debug("[{}]: element [{}] moved to last position = [{}]", this.UNIQ_ID, tempElement.getELEMENT(), lastCachePosition);
    }

    private CacheElement<T> getCacheElementByIndex(int index) throws ElementNotFoundByIndex {
        for (CacheElement<T> cacheElement : CACHE_ELEMENTS) {
            if (cacheElement.getINDEX() == index) {
                log.debug("[{}]: method returned CacheElement {}", this.UNIQ_ID, cacheElement);
                return cacheElement;
            }
        }
        log.error("requested element is not found");
        throw new ElementNotFoundByIndex(index);
    }

    private int getElementPositionByIndex(int index) throws ElementNotFoundByIndex {
        for (int position = 0; position < CACHE_ELEMENTS.length; position++) {
            if (CACHE_ELEMENTS[position].getINDEX() == index) {
                log.debug("[{}]: element [{}] identified with position = [{}] in cache", this.UNIQ_ID, CACHE_ELEMENTS[position].getELEMENT(), position);
                return position;
            }
        }
        log.error("requested element is not found");
        throw new ElementNotFoundByIndex(index);
    }

}