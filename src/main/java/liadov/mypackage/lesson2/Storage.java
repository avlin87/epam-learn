package liadov.mypackage.lesson2;

import liadov.mypackage.lesson4.IllegalStateOfCacheElement;
import liadov.mypackage.lesson4.ElementDoesNotExistException;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class Storage<T> {
    private Object[] storage;
    private Cache<T> cache;
    private int countStorageElements = 0;
    private int storageCapacity;

    /**
     * Default constructor.
     */
    public Storage() {
        storageCapacity = 10;
        storage = new Object[storageCapacity];
        cache = new Cache<>(storageCapacity);
        log.info("Storage [{}] with default parameters created", this.hashCode());
    }

    /**
     * Constructor that populates storage with received elements.
     *
     * @param elements Those elements will be stored in storage.
     */
    public Storage(T[] elements) {
        storageCapacity = elements.length;
        storage = new Object[storageCapacity];
        for (int i = 0; i < elements.length; i++) {
            storage[i] = elements[i];
            countStorageElements++;
        }
        cache = new Cache<>(10);
        log.info("Storage [{}]: created with received elements.\nType of elements: [{}]", this.hashCode(), elements[0].getClass().getName());
        log.debug("Storage [{}]: state:\n{}", this.hashCode(), this.toString());
    }

    /**
     * Method adds received element to storage if element is not null
     *
     * @param element This element will be added to storage if element is not null
     */
    public void add(T element) {
        log.info("Storage [{}]: element [{}] <{}> will be added with index [{}]", this.hashCode(), element, element.getClass().getName(), (countStorageElements + 1));
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
        log.info("Storage [{}]: element [{}] with index [{}]: will be deleted", this.hashCode(), storage[countStorageElements - 1], (countStorageElements - 1));
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
        log.info("Storage [{}]: cleared", this.hashCode());
    }

    /**
     * Method returns last element of storage
     *
     * @return element
     */
    @SuppressWarnings("unchecked")
    public T getLast() {
        log.debug("Storage [{}]: returns element [{}] with index [{}]", this.hashCode(), storage[countStorageElements - 1], (countStorageElements - 1));
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
            log.error(e.getFullStackTrace());
        }
        try {
            if (index >= storage.length) {
                throw new ElementDoesNotExistException(index);
            }
            cache.add((T) storage[index], index);
            log.debug("Storage [{}]: returns element [{}] with index [{}]", this.hashCode(), storage[index], index);
            return (T) storage[index];
        } catch (ElementDoesNotExistException e) {
            log.error(e.getFullStackTrace());
        }
        return null;
    }

    /**
     * Method increase capacity of storage
     */
    private void increaseCapacityOfStorage() {
        log.debug("Storage [{}]: capacity before increase {}", this.hashCode(), storageCapacity);
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
        log.debug("Storage [{}]: capacity increased TO {}", this.hashCode(), storageCapacity);
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
