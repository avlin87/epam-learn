package com.liadov.cat.lesson2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CacheElementTest {

    @Test
    public void equalsShouldReturnTrue() {
        CacheElement<String> cacheElement1 = new CacheElement<>("testElement", 1);
        CacheElement<String> cacheElement2 = new CacheElement<>("testElement", 1);

        boolean actualResult = cacheElement1.equals(cacheElement2);

        assertTrue(actualResult);
    }

    @Test
    public void equalsShouldReturnFalse() {
        CacheElement<String> cacheElement1 = new CacheElement<>("testElement", 0);
        CacheElement<String> cacheElement2 = new CacheElement<>("testElement", 1);

        boolean actualResult = cacheElement1.equals(cacheElement2);

        assertFalse(actualResult);
    }

    @Test
    public void equalsWithNullReturnFalse() {
        CacheElement<String> cacheElement1 = new CacheElement<>("testElement", 0);
        CacheElement<String> cacheElement2 = new CacheElement<>(null, 0);

        boolean actualResult = cacheElement1.equals(cacheElement2);

        assertFalse(actualResult);
    }

}