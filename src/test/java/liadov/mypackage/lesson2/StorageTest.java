package liadov.mypackage.lesson2;

import org.junit.jupiter.api.Test;
import liadov.mypackage.lesson4.ElementDoesNotExistException;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @Test
    void addShouldPutElementToStorage() {
        Storage<String> storage = new Storage<>();
        storage.add("testElement");
        assertEquals(storage.getLast(), "testElement");
    }

    @Test
    void deleteShouldRemoveLastElement() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 3"});
        assertNotNull(storage.get(0));
        storage.delete();
        assertNull(storage.get(0));
    }

    @Test
    void clearShouldRemoveAllFromStorage() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});
        assertNotNull(storage.get(0));
        storage.clear();
        assertNull(storage.get(0));
    }

    @Test
    void getLastShouldReturnLastElement() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});
        assertEquals(storage.getLast(), "testElement 3");
    }

    @Test
    void getElementByIndex() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});
        assertEquals(storage.get(1), "testElement 2");
    }

    @Test
    void getElementByIndexThrowsElementDoesNotExistException() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});
        assertThrows(ElementDoesNotExistException.class, () -> storage.get(80));
    }

    @Test
    void getReturnNull() {
        Storage<String> storage = new Storage<>();
        storage.add("testElement 1");
        storage.add("testElement 2");
        storage.add("testElement 3");
        assertNull(storage.get(4));
    }


    @Test
    void testIncreaseCapacityOfStorage() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});
        System.out.println(storage.toString());
    }

    @Test
    void testToStringShouldRepresentStateOfStorage() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});
        storage.get(1);
        storage.get(0);
        storage.get(2);
        assertTrue(storage.toString().startsWith("Storage{storage=[testElement 1, testElement 2, testElement 3], countStorageElements=3, storageCapacity=3, CACHE=[Cache-"));
        assertTrue(storage.toString().endsWith("]: {cacheCapacity=10, countCacheElements=3, cache=[CE{e=testElement 2, i=1}, CE{e=testElement 1, i=0}, CE{e=testElement 3, i=2}, null, null, null, null, null, null, null]}}"));
    }
}