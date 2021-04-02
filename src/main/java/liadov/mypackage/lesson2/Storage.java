package liadov.mypackage.lesson2;

import liadov.mypackage.lesson4.MyCheckedException;
import liadov.mypackage.lesson4.MyUncheckedArrayIndexOutOfBoundsException;
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
        log.info(String.format("Storage [%d] with default parameters created", this.hashCode()));
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
        log.info(String.format("Storage [%d]: created with received elements.\nType of elements: [%s]", this.hashCode(), elements[0].getClass().getName()));
        log.debug(String.format("Storage [%d]: state:\n%s", this.hashCode(), this.toString()));
    }

    /**
     * Method adds received element to storage if element is not null
     *
     * @param element This element will be added to storage if element is not null
     */
    public void add(T element) {
        log.info(String.format("Storage [%d]: element [%s] <%s> will be added with index [%d]", this.hashCode(), element, element.getClass().getName(), (countStorageElements + 1)));
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
        log.info(String.format("Storage [%d]: element [%s] with index [%d]: will be deleted", this.hashCode(), storage[countStorageElements - 1], (countStorageElements - 1)));
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
        log.info(String.format("Storage [%d]: cleared", this.hashCode()));
    }

    /**
     * Method returns last element of storage
     *
     * @return element
     */
    @SuppressWarnings("unchecked")
    public T getLast() {
        log.debug(String.format("Storage [%d]: returns element [%s] with index [%d]", this.hashCode(), storage[countStorageElements - 1], (countStorageElements - 1)));
        return (T) storage[countStorageElements - 1];
    }

    /**
     * Method returns requested element of storage by index
     * return null in case element was not found
     *
     * @param index element with this index will be returned from storage
     * @return requested element
     * @throws MyUncheckedArrayIndexOutOfBoundsException in case requested element out of range of current storage
     */
    @SuppressWarnings("unchecked")
    public T get(int index) throws MyUncheckedArrayIndexOutOfBoundsException {
        try {
            if (cache.isPresent(index)) {
                return cache.get(index);
            }
        } catch (MyCheckedException e) {
            log.error(e.getFullStackTrace());
        }
        try {
            if (index >= storage.length) {
                throw new MyUncheckedArrayIndexOutOfBoundsException(index);
            }
            cache.add((T) storage[index], index);
            log.debug(String.format("Storage [%d]: returns element [%s] with index [%d]", this.hashCode(), storage[index], index));
            return (T) storage[index];
        } catch (MyUncheckedArrayIndexOutOfBoundsException e) {
            log.error(e.getFullStackTrace());
        }
        return null;
    }

    /**
     * Method increase capacity of storage
     */
    private void increaseCapacityOfStorage() {
        log.debug(String.format("Storage [%d]: capacity before increase %d", this.hashCode(), storageCapacity));
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
        log.debug(String.format("Storage [%d]: capacity increased TO %d", this.hashCode(), storageCapacity));
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
