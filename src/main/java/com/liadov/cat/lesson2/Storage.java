package com.liadov.cat.lesson2;

import com.liadov.cat.lesson4.IllegalStateOfElement;
import com.liadov.cat.lesson4.ElementNotFoundByIndex;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.UUID;

/**
 * Storage saves provided information of specified type
 *
 * @param <T> type of element value to be stored in storage.
 * @author Aleksandr Liadov
 * @version 1.10 17 April 2020
 */
@Slf4j
public class Storage<T> {

    private final String UNIQ_ID;
    private final Cache<T> cache;

    private Object[] storage;
    private int countStorageElements = 0;
    private int storageCapacity;

    public Storage() {
        UNIQ_ID = generateUniqId();
        storageCapacity = 10;
        storage = new Object[storageCapacity];
        cache = new Cache<>(storageCapacity);
        log.debug("[{}] with default parameters created", this.UNIQ_ID);
    }

    /**
     * Constructor that populates storage with received elements.
     *
     * @param elements Those elements will be stored in storage.
     */
    public Storage(T[] elements) {
        UNIQ_ID = generateUniqId();
        try {
            fillStorageWithElements(elements);
        } catch (IllegalStateOfElement e) {
            log.error(e.toString());
            throw e;
        }
        cache = new Cache<>(10);
        log.debug("[{}]: created with received elements.\nType of elements: [{}]", this.UNIQ_ID, elements[0].getClass().getName());
    }

    /**
     * Method adds received element to storage if element is not null
     *
     * @param element This element will be added to storage
     */
    public void add(T element) {
        if (element == null) {
            log.error("[{}]:element is null", this.UNIQ_ID);
            throw new IllegalStateOfElement("Element is null");
        }
        if (countStorageElements >= storage.length) {
            increaseCapacityOfStorage();
        }
        storage[countStorageElements++] = element;
        log.info("[{}]: element [{}] <{}> added with index [{}]", this.UNIQ_ID, element, element.getClass().getName(), (countStorageElements + 1));
    }

    /**
     * Method removes last element from storage
     */
    @SuppressWarnings("unchecked")
    public void delete() {
        if (countStorageElements < 1) {
            log.error("[{}]: No elements present in storage", this.UNIQ_ID);
            throw new ElementNotFoundByIndex(countStorageElements);
        }

        log.info("[{}]: element [{}] with index [{}]: will be deleted", this.UNIQ_ID, storage[countStorageElements - 1], (countStorageElements - 1));

        if (cache.isPresent((T) storage[countStorageElements - 1])) {
            cache.delete((T) storage[countStorageElements - 1]);
        }
        storage[--countStorageElements] = null;
    }

    /**
     * Method remove everything from Storage and Cache
     */
    public void clear() {
        for (int i = 0; i < storage.length; i++) {
            log.info("[{}]:Element removed [{}]", this.UNIQ_ID, storage[i]);
            storage[i] = null;
        }
        countStorageElements = 0;
        cache.clear();
        log.info("[{}]: cleared successfully", this.UNIQ_ID);
    }

    /**
     * Method returns last element of storage
     *
     * @return element
     */
    @SuppressWarnings("unchecked")
    public T getLast() {
        if (countStorageElements < 1) {
            log.error("[{}]: No elements present in storage", this.UNIQ_ID);
            throw new ElementNotFoundByIndex(countStorageElements);
        }

        log.debug("[{}]: returns element [{}] with index [{}]", this.UNIQ_ID, storage[countStorageElements - 1], (countStorageElements - 1));
        return (T) storage[countStorageElements - 1];
    }

    /**
     * Method returns requested element of storage by index
     *
     * @param index element with this index will be returned from storage
     * @return requested element
     * @throws ElementNotFoundByIndex in case requested index is not present in current storage
     */
    @SuppressWarnings("unchecked")
    public T get(int index) throws ElementNotFoundByIndex {
        if (index >= countStorageElements) {
            log.error("[{}]: count of elements in storage = {} less then requested index = {}", this.UNIQ_ID, index, countStorageElements);
            throw new ElementNotFoundByIndex(index);
        }
        if (cache.isPresent(index)) {
            log.debug("[{}]: returns element [{}] with index [{}]", this.UNIQ_ID, cache.get(index), index);
            return cache.get(index);
        }
        cache.add((T) storage[index], index);
        log.debug("[{}]: returns element [{}] with index [{}]", this.UNIQ_ID, storage[index], index);
        return (T) storage[index];
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }

    public int getCountStorageElements() {
        return countStorageElements;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "storage=" + Arrays.toString(storage) +
                ", countStorageElements=" + countStorageElements +
                ", storageCapacity=" + storageCapacity +
                ", CACHE=" + cache +
                '}';
    }

    private String generateUniqId() {
        return "Storage-" + UUID.randomUUID().toString();
    }

    private void fillStorageWithElements(T[] elements) {
        if (elements == null) {
            throw new IllegalStateOfElement("Storage received null instead of elements");
        }
        storageCapacity = elements.length;
        storage = new Object[storageCapacity];
        for (int i = 0; i < elements.length; i++) {
            storage[i] = elements[i];
            log.debug("storage received initial element = [{}]", elements[i]);
            countStorageElements++;
        }
    }

    private void increaseCapacityOfStorage() {
        log.debug("[{}]: capacity before increase {}", this.UNIQ_ID, storageCapacity);
        if (storageCapacity < 1) {
            storageCapacity = 1;
        } else if (storageCapacity < 2) {
            storageCapacity = 2 * storageCapacity;
        } else {
            storageCapacity = (int) (1.5 * storageCapacity);
        }
        Object[] tempStorage = new Object[storageCapacity];
        for (int i = 0; i < storage.length; i++) {
            tempStorage[i] = storage[i];
        }
        storage = tempStorage;
        log.debug("[{}]: capacity increased TO {}", this.UNIQ_ID, storageCapacity);
    }
}
