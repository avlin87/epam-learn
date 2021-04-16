package com.liadov.cat.lesson2;

import com.liadov.cat.lesson4.IllegalStateOfCacheElement;
import com.liadov.cat.lesson4.ElementDoesNotExistException;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
public class Storage<T> {
    private Object[] storage;
    private Cache<T> cache;
    private int countStorageElements = 0;

    private int storageCapacity;
    private final String UNIQ_ID;

    /**
     * Default constructor.
     */
    public Storage() {
        UNIQ_ID = generateUniqId();
        storageCapacity = 10;
        storage = new Object[storageCapacity];
        cache = new Cache<>(storageCapacity);
        log.info("[{}] with default parameters created", this.UNIQ_ID);
    }

    /**
     * Constructor that populates storage with received elements.
     *
     * @param elements Those elements will be stored in storage.
     */
    public Storage(T[] elements) {
        UNIQ_ID = generateUniqId();
        storageCapacity = elements.length;
        storage = new Object[storageCapacity];
        for (int i = 0; i < elements.length; i++) {
            storage[i] = elements[i];
            countStorageElements++;
        }
        cache = new Cache<>(10);
        log.info("[{}]: created with received elements.\nType of elements: [{}]", this.UNIQ_ID, elements[0].getClass().getName());
        log.debug("[{}]: state:\n{}", this.UNIQ_ID, this.toString());
    }

    /**
     * Method generate unique identifier based on java.util.UUID
     *
     * @return String
     */
    private String generateUniqId() {
        return "Storage-" + UUID.randomUUID().toString();
    }

    /**
     * Method adds received element to storage if element is not null
     *
     * @param element This element will be added to storage if element is not null
     */
    public void add(T element) {
        log.info("[{}]: element [{}] <{}> will be added with index [{}]", this.UNIQ_ID, element, element.getClass().getName(), (countStorageElements + 1));
        if (element != null) {
            if (countStorageElements >= storage.length) {
                increaseCapacityOfStorage();
            }
            storage[countStorageElements++] = element;
        }
    }

    /**
     * Method removes last element from storage
     */
    @SuppressWarnings("unchecked")
    public void delete() {
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
            storage[i] = null;
        }
        countStorageElements = 0;
        cache.clear();
        log.info("[{}]: cleared", this.UNIQ_ID);
    }

    /**
     * Method returns last element of storage
     *
     * @return element
     */
    @SuppressWarnings("unchecked")
    public T getLast() {
        log.debug("[{}]: returns element [{}] with index [{}]", this.UNIQ_ID, storage[countStorageElements - 1], (countStorageElements - 1));
        return (T) storage[countStorageElements - 1];
    }

    /**
     * Method returns requested element of storage by index
     * return null in case element was not found
     *
     * @param index element with this index will be returned from storage
     * @return requested element
     * @throws ElementDoesNotExistException in case requested index is not present in current storage
     */
    @SuppressWarnings("unchecked")
    public T get(int index) throws ElementDoesNotExistException {
        try {
            if (cache.isPresent(index)) {
                return cache.get(index);
            }
        } catch (IllegalStateOfCacheElement e) {
            log.error(e.toString());
        }
        try {
            if (index >= storage.length) {
                throw new ElementDoesNotExistException(index);
            }
            cache.add((T) storage[index], index);
            log.debug("[{}]: returns element [{}] with index [{}]", this.UNIQ_ID, storage[index], index);
            return (T) storage[index];
        } catch (ElementDoesNotExistException e) {
            log.error(e.toString());
            throw e;
        }
    }

    /**
     * Method increase capacity of storage
     */
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

    /**
     * Method return current Storage size
     * @return int
     */
    public int getStorageCapacity() {
        return storageCapacity;
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
}
