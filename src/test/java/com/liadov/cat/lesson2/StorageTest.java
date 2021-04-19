package com.liadov.cat.lesson2;

import com.liadov.cat.exceptions.IllegalStateOfElement;
import org.junit.jupiter.api.Test;
import com.liadov.cat.exceptions.ElementNotFoundByIndex;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StorageTest {

    @Test
    public void storageReceiveNullElementsThrowIllegalStateOfElementException() {
        assertThrows(IllegalStateOfElement.class, () -> new Storage<String>(null));
    }

    @Test
    public void addShouldPutElementToStorage() {
        Storage<String> storage = new Storage<>();

        storage.add("testElement");

        assertEquals("testElement", storage.getLast());
    }

    @Test
    public void addNullThrowException() {
        Storage<String> storage = new Storage<>();

        assertThrows(NullPointerException.class, () -> storage.add(null));
    }

    @Test
    public void deleteShouldRemoveLastElement() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});

        storage.delete();

        assertEquals(2, storage.getCountStorageElements());
        assertThrows(ElementNotFoundByIndex.class, () -> storage.get(2));
    }

    @Test
    public void deleteThrowExceptionIfStorageEmpty() {
        Storage<String> storage = new Storage<>();

        assertThrows(ElementNotFoundByIndex.class, () -> storage.delete());
    }

    @Test
    public void clearShouldRemoveAllFromStorage() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});

        storage.clear();

        assertEquals(0, storage.getCountStorageElements());
    }

    @Test
    public void getLastShouldReturnLastElement() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});

        String actualResult = storage.getLast();

        assertEquals("testElement 3", actualResult);
    }

    @Test
    public void getElementByIndex() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});

        String actualResult = storage.get(1);

        assertEquals("testElement 2", actualResult);
    }

    @Test
    public void getElementByIndexThrowsElementNotFoundByIndexException() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});

        assertThrows(ElementNotFoundByIndex.class, () -> storage.get(80));
    }

    @Test
    public void testIncreaseCapacityOfStorage() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});
        storage.add("testElement 4");
        storage.add("testElement 5");
        storage.add("testElement 6");
        storage.add("testElement 7");

        int actualResult = storage.getStorageCapacity();

        assertEquals(9, actualResult);
    }

    @Test
    public void testToStringShouldRepresentStateOfStorage() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});
        storage.get(1);
        storage.get(0);
        storage.get(2);

        boolean actualResult = storage.toString().startsWith("Storage{storage=[testElement 1, testElement 2, testElement 3], countStorageElements=3, storageCapacity=3, CACHE=[Cache-")
                && storage.toString().endsWith("]: {cacheCapacity=10, countCacheElements=3, cache=[CE{e=testElement 2, i=1}, CE{e=testElement 1, i=0}, CE{e=testElement 3, i=2}, null, null, null, null, null, null, null]}}");

        assertTrue(actualResult);
    }
}