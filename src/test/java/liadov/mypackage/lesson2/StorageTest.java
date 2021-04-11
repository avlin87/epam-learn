package liadov.mypackage.lesson2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @Test
    void addShouldPutElementToStorage() {
        Storage<String> storage = new Storage<>();
        storage.add("testElement");
        assertTrue("testElement".equals(storage.getLast()));
    }

    @Test
    void delete() {
    }

    @Test
    void clear() {
    }

    @Test
    void getLast() {
    }

    @Test
    void get() {
    }

    @Test
    void testToString() {
    }
}