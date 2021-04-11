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
        Storage<String> storage = new Storage<>(new String[]{"testElement 1","testElement 2","testElement 3"});
        assertNotNull(storage.get(0));
        storage.clear();
        assertNull(storage.get(0));
    }

    @Test
    void getLastShouldReturnLastElement() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1","testElement 2","testElement 3"});
        assertEquals(storage.getLast(), "testElement 3");
    }

    @Test
    void getElementByIndex() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1","testElement 2","testElement 3"});
        assertEquals(storage.get(1), "testElement 2");
    }

    @Test
    void getReturnNull() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1","testElement 2","testElement 3"});
        assertNull(storage.get(50));
    }

    @Test
    void testToString() {
    }
}