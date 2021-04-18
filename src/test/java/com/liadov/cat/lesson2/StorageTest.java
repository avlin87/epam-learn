package com.liadov.cat.lesson2;

import com.liadov.cat.lesson4.IllegalStateOfElement;
import org.junit.jupiter.api.Test;
import com.liadov.cat.lesson4.ElementNotFoundByIndex;

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
        assertEquals(storage.getLast(), "testElement");
    }

    @Test
    public void deleteShouldRemoveLastElement() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});
        assertNotNull(storage.get(2));
        storage.delete();
        assertEquals(2, storage.getCountStorageElements());
        assertThrows(ElementNotFoundByIndex.class, () -> storage.get(2));
    }

    @Test
    public void clearShouldRemoveAllFromStorage() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});
        assertEquals(3, storage.getStorageCapacity());
        storage.clear();
        assertEquals(0, storage.getCountStorageElements());
    }

    @Test
    public void getLastShouldReturnLastElement() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});
        assertEquals(storage.getLast(), "testElement 3");
    }

    @Test
    public void getElementByIndex() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});
        assertEquals(storage.get(1), "testElement 2");
    }

    @Test
    public void getElementByIndexThrowsElementNotFoundByIndexException() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});
        assertThrows(ElementNotFoundByIndex.class, () -> storage.get(80));
    }

    @Test
    public void testIncreaseCapacityOfStorage() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});
        int oldStorageCapacity = 3;
        int newStorageCapacity = storage.getStorageCapacity();
        assertEquals(oldStorageCapacity, newStorageCapacity);
        storage.add("testElement 4");
        newStorageCapacity = storage.getStorageCapacity();
        assertEquals((int) (1.5 * oldStorageCapacity), newStorageCapacity);
        storage.add("testElement 5");
        oldStorageCapacity = newStorageCapacity;
        newStorageCapacity = storage.getStorageCapacity();
        assertEquals((int) (1.5 * oldStorageCapacity), newStorageCapacity);
        storage.add("testElement 6");
        storage.add("testElement 7");
        oldStorageCapacity = newStorageCapacity;
        newStorageCapacity = storage.getStorageCapacity();
        assertEquals((int) (1.5 * oldStorageCapacity), newStorageCapacity);
    }

    @Test
    public void testToStringShouldRepresentStateOfStorage() {
        Storage<String> storage = new Storage<>(new String[]{"testElement 1", "testElement 2", "testElement 3"});
        storage.get(1);
        storage.get(0);
        storage.get(2);
        assertTrue(storage.toString().startsWith("Storage{storage=[testElement 1, testElement 2, testElement 3], countStorageElements=3, storageCapacity=3, CACHE=[Cache-"));
        assertTrue(storage.toString().endsWith("]: {cacheCapacity=10, countCacheElements=3, cache=[CE{e=testElement 2, i=1}, CE{e=testElement 1, i=0}, CE{e=testElement 3, i=2}, null, null, null, null, null, null, null]}}"));
    }
}