package liadov.mypackage.lesson2;

import liadov.mypackage.lesson4.IllegalStateOfCacheElement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    @Test
    void addShouldPutElementToCache() {
        Cache<String> cache = new Cache<>(1);
        cache.add("testElement",0);
        assertTrue(cache.isPresent("testElement"));
    }

    @Test
    void deleteShouldRemoveElementFromCache() {
        Cache<String> cache = new Cache<>(1);
        cache.add("testElement",0);
        cache.delete("testElement");
        assertFalse(cache.isPresent("testElement"));
    }


    @Test
    void testIsPresentByIndexShouldReturnTrue() {
        Cache<String> cache = new Cache<>(10);
        cache.add("testElement",18);
        assertTrue(cache.isPresent(18));
    }

    @Test
    void testIsPresentByIndexShouldReturnFalse() {
        Cache<String> cache = new Cache<>(10);
        cache.add("testElement",18);
        assertTrue(cache.isPresent(2));
    }


    @Test
    void testIsPresentByValueShouldReturnTrue() {
        Cache<Double> cache = new Cache<>(1);
        cache.add(123456.00,0);
        assertTrue(cache.isPresent(123456.00));
    }

    @Test
    void testIsPresentByValueShouldReturnFalse() {
        Cache<String> cache = new Cache<>(1);
        cache.add("testElement",0);
        assertFalse(cache.isPresent("falseTestElement"));
    }

    @Test
    void getShouldReturnNull() throws IllegalStateOfCacheElement {
        Cache<String> cache = new Cache<>(1);
        cache.add("testElement",0);
        assertNull(cache.get(1));
    }

    @Test
    void getShouldReturnElement() throws IllegalStateOfCacheElement {
        Cache<String> cache = new Cache<>(1);
        cache.add("testElement",0);
        assertEquals(cache.get(0),"testElement");
    }

    @Test
    void clearShouldRemoveElementsFromCache() {
        Cache<String> cache = new Cache<>(1);
        cache.add("testElement",0);
        assertTrue(cache.isPresent("testElement"));
        cache.clear();
        assertFalse(cache.isPresent("testElement"));
    }

    @Test
    void testToStringShouldReturnStataOfCache() {
        Cache<String> cache = new Cache<>(1);
        assertTrue(cache.toString().endsWith("]: {cacheCapacity=1, countCacheElements=0, cache=[null]}"));
        cache.add("testElement",0);
        assertTrue(cache.toString().endsWith("]: {cacheCapacity=1, countCacheElements=1, cache=[CE{e=testElement, i=0}]}"));
    }
}