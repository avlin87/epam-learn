package liadov.mypackage.lesson2;

public class Cache<T> {
    private Object[] cache;
    private int capacity;
    private int countElements = 0;

    /**
     * Cache constructor with initialization of cache according to received capacity
     *
     * @param capacity
     */
    public Cache(int capacity) {
        this.capacity = capacity;
        cache = new Object[capacity];
    }

    /**
     * Method for adding element to Cache.
     *
     * @param element This element will be added to Cache
     */
    public void add(T element) {
        if (countElements < capacity) {
            cache[countElements++] = element;
        } else {
            cache[0] = null;
            moveLeftAllCacheElements();
            cache[capacity - 1] = element;
        }
        printCache();
    }

    /**
     * Method for removing element from Cache
     *
     * @param element This element will be removed from Cache if present
     */
    public void delete(T element) {
        if (isPresent(element)) {
            for (int i = getElementID(element); i < cache.length - 1; i++) {
                cache[i] = cache[i + 1];
            }
            cache[--countElements] = null;
        }
        printCache();
    }

    /**
     * Method for checking whether element is present in Cache
     *
     * @param element the presence of this element is checked
     * @return boolean
     */
    public boolean isPresent(T element) {
        for (int i = 0; i < cache.length; i++) {
            if (cache[i] != null && cache[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method returns requested element from Cache
     *
     * @param element this element will found in Cache and moved to top
     * @return requested element
     */
    @SuppressWarnings("unchecked")
    public T get(T element) {
        if (isPresent(element)) {
            for (int i = getElementID(element); i < cache.length - 1; i++) {
                cache[i] = cache[i + 1];
            }
            cache[countElements - 1] = element;
            return (T) cache[countElements - 1];
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

    private void moveLeftAllCacheElements() {
        for (int i = 0; i < cache.length - 1; i++) {
            cache[i] = cache[i + 1];
        }
    }

    private int getElementID(T element) {
        for (int i = 0; i < cache.length; i++) {
            if (cache[i].equals(element)) {
                return i;
            }
        }
        return capacity;
    }

    /*to be removed*/
    private void printCache() {
        for (int i = 0; i < cache.length; i++) {
            System.out.print(cache[i] + " ");
        }
        System.out.println();
    }
}