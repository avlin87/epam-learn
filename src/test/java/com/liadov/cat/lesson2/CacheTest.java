package com.liadov.cat.lesson2;

import com.liadov.cat.exceptions.ElementNotFoundByIndex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    @Test
    public void addShouldPutElementToCache() {
        Cache<String> cache = new Cache<>(1);

        cache.add("testElement", 0);

        assertTrue(cache.isPresent("testElement"));
    }

    @Test
    public void deleteShouldRemoveElementFromCache() {
        Cache<String> cache = new Cache<>(1);
        cache.add("testElement", 0);

        cache.delete("testElement");

        assertFalse(cache.isPresent("testElement"));
    }


    @Test
    public void isPresentByIndexShouldReturnTrue() {
        Cache<String> cache = new Cache<>(10);
        cache.add("testElement", 18);

        boolean actualResult = cache.isPresent(18);

        assertTrue(actualResult);
    }

    @Test
    public void isPresentByIndexShouldReturnFalse() {
        Cache<String> cache = new Cache<>(10);
        cache.add("testElement", 18);

        boolean actualResult = cache.isPresent(2);

        assertFalse(actualResult);
    }


    @Test
    public void isPresentByValueShouldReturnTrue() {
        Cache<Double> cache = new Cache<>(1);
        cache.add(123456.00, 0);

        boolean actualResult = cache.isPresent(123456.00);

        assertTrue(actualResult);
    }

    @Test
    public void isPresentByValueShouldReturnFalse() {
        Cache<String> cache = new Cache<>(1);
        cache.add("testElement", 0);

        boolean actualResult = cache.isPresent("falseTestElement");

        assertFalse(actualResult);
    }

    @Test
    public void getTrowsElementNotFoundByIndexException() {
        Cache<String> cache = new Cache<>(1);
        cache.add("testElement", 0);

        assertThrows(ElementNotFoundByIndex.class, () -> cache.get(1));
    }

    @Test
    public void getShouldReturnElement() {
        Cache<String> cache = new Cache<>(1);
        cache.add("testElement", 0);

        String actualResult = cache.get(0);

        assertEquals("testElement", actualResult);
    }

    @Test
    public void clearShouldRemoveElementsFromCache() {
        Cache<String> cache = new Cache<>(1);
        cache.add("testElement", 0);

        cache.clear();

        assertFalse(cache.isPresent("testElement"));
    }

    @Test
    public void toStringShouldReturnStataOfCache() {
        Cache<String> cache = new Cache<>(1);

        cache.add("testElement", 0);

        assertTrue(cache.toString().endsWith("]: {cacheCapacity=1, countCacheElements=1, cache=[CE{e=testElement, i=0}]}"));
    }

    @Test
    public void moveLeftCacheElementsShouldBeExecutedOnReachingCapacity() {
        Cache<String> cache = new Cache<>(3);
        cache.add("testElement1", 1);
        cache.add("testElement2", 2);
        cache.add("testElement3", 3);

        cache.add("testElement4", 4);

        assertTrue(cache.toString().endsWith("]: {cacheCapacity=3, countCacheElements=3, cache=[CE{e=testElement2, i=2}, CE{e=testElement3, i=3}, CE{e=testElement4, i=4}]}"));
    }

    @Test
    public void moveLeftCacheElementsShouldBeExecutedOnDelete() {
        Cache<String> cache = new Cache<>(3);
        cache.add("testElement1", 1);
        cache.add("testElement2", 2);
        cache.add("testElement3", 3);
        cache.add("testElement4", 4);

        cache.delete("testElement3");

        assertTrue(cache.toString().endsWith("]: {cacheCapacity=3, countCacheElements=2, cache=[CE{e=testElement2, i=2}, CE{e=testElement4, i=4}, null]}"));
    }
}