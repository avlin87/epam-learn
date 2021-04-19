package com.liadov.cat.lesson2;

import com.liadov.cat.lesson4.IllegalStateOfElement;
import com.liadov.cat.lesson4.ElementNotFoundByIndex;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.UUID;

/**
 * Storage saves provided information of specified type
 *
 * @param <T> type of element value to be stored in storage.
 * @author Aleksandr Liadov
 */
@Slf4j
public class Storage<T> {

    private final String uniq_id;
    private final Cache<T> cache;

    private Object[] storage;
    private int countStorageElements = 0;
    private int storageCapacity;

    public Storage() {
        uniq_id = generateUniqId();
        storageCapacity = 10;
        storage = new Object[storageCapacity];
        cache = new Cache<>(storageCapacity);
        log.info("[{}] with default parameters created", this.uniq_id);
    }

    /**
     * Constructor that populates storage with received elements.
     *
     * @param elements Those elements will be stored in storage.
     */
    public Storage(T[] elements) {
        uniq_id = generateUniqId();
        try {
            fillStorageWithElements(elements);
        } catch (IllegalStateOfElement e) {
            log.error("Failed to fill storage elements from existing source", e);
            throw e;
        }
        cache = new Cache<>(10);
        log.info("[{}]: created with received elements.\nType of elements: [{}]", this.uniq_id, elements[0].getClass().getName());
    }

    /**
     * Method adds received element to storage if element is not null
     *
     * @param element This element will be added to storage
     */
    public void add(@NonNull T element) {
        if (element == null) {
            log.error("[{}]:element is null", this.uniq_id);
            throw new IllegalStateOfElement("Element is null");
        }
        if (countStorageElements >= storage.length) {
            increaseCapacityOfStorage();
        }
        storage[countStorageElements++] = element;
        log.info("[{}]: element [{}] <{}> added with index [{}]", this.uniq_id, element, element.getClass().getName(), (countStorageElements + 1));
    }

    /**
     * Method removes last element from storage
     */
    @SuppressWarnings("unchecked")
    public void delete() {
        if (countStorageElements < 1) {
            log.error("[{}]: No elements present in storage", this.uniq_id);
            throw new ElementNotFoundByIndex(countStorageElements);
        }

        log.info("[{}]: element [{}] with index [{}]: will be deleted", this.uniq_id, storage[countStorageElements - 1], (countStorageElements - 1));

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
            log.debug("[{}]:Element removed [{}]", this.uniq_id, storage[i]);
            storage[i] = null;
        }
        countStorageElements = 0;
        cache.clear();
        log.info("[{}]: cleared successfully", this.uniq_id);
    }

    /**
     * Method returns last element of storage
     *
     * @return element
     */
    @SuppressWarnings("unchecked")
    public T getLast() {
        if (countStorageElements < 1) {
            log.error("[{}]: No elements present in storage", this.uniq_id);
            throw new ElementNotFoundByIndex(countStorageElements);
        }

        log.info("[{}]: returns element [{}] with index [{}] from storage", this.uniq_id, storage[countStorageElements - 1], (countStorageElements - 1));
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
            log.error("[{}]: count of elements in storage = {}. requested index = {}", this.uniq_id, index, countStorageElements);
            throw new ElementNotFoundByIndex(index);
        }
        if (cache.isPresent(index)) {
            log.info("[{}]: returns element [{}] with index [{}] from cache", this.uniq_id, cache.get(index), index);
            return cache.get(index);
        }
        cache.add((T) storage[index], index);
        log.info("[{}]: returns element [{}] with index [{}] from storage", this.uniq_id, storage[index], index);
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
        log.debug("[{}]: capacity before increase {}", this.uniq_id, storageCapacity);
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
        log.debug("[{}]: capacity increased TO {}", this.uniq_id, storageCapacity);
    }
}
