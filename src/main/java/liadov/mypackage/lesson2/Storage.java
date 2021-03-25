package liadov.mypackage.lesson2;

import java.util.Arrays;

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
        cache = new Cache<>(storage.length);
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
        cache = new Cache<>(storage.length);
    }

    /**
     * Method adds received element to storage
     *
     * @param element This element will be added to storage
     */
    public void add(T element) {
        if (countStorageElements >= storage.length) {
            increaseCapacityOfStorage();
        }
        storage[countStorageElements++] = element;
    }

    /**
     * Method removes last element from storage
     */
    @SuppressWarnings("unchecked")
    public void delete() {
        if (cache.isPresent((T) storage[countStorageElements - 1])) {
            cache.delete((T) storage[countStorageElements - 1]);
        }
        storage[--countStorageElements] = null;
    }

    /**
     * Method remove everything from Cache
     */
    public void clear() {
        cache.clear();
    }

    /**
     * Method returns last element of storage
     *
     * @return element
     */
    @SuppressWarnings("unchecked")
    public T getLast() {
        return (T) storage[countStorageElements - 1];
    }

    /**
     * Method returns requested element of storage by index
     * @param index element with this index will be returned from storage
     * @return requested element
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (cache.isPresent((T) storage[index])) {
            return cache.get((T) storage[index]);
        }
        cache.add((T) storage[index]);
        return (T) storage[index];
    }

    /**
     * Method increase capacity of storage
     */
    private void increaseCapacityOfStorage() {
        if (storageCapacity < 1)
            storageCapacity = 1;
        else if (storageCapacity < 2)
            storageCapacity = 2 * storageCapacity;
        else
            storageCapacity = (int) (1.5 * storageCapacity);
        Object[] tempStorage = new Object[storageCapacity];
        for (int i = 0; i < storage.length; i++) {
            tempStorage[i] = storage[i];
        }
        storage = tempStorage;
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
