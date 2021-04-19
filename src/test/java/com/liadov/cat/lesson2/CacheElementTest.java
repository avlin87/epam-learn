package com.liadov.cat.lesson2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CacheElementTest {

    @Test
    public void testEqualsShouldReturnTrue() {
        CacheElement<String> cacheElement1 = new CacheElement<>("testElement", 1);
        CacheElement<String> cacheElement2 = new CacheElement<>("testElement", 1);
        assertEquals(cacheElement1, cacheElement2);
    }

    @Test
    public void testEqualsShouldReturnFalse() {
        CacheElement<String> cacheElement1 = new CacheElement<>("testElement", 0);
        CacheElement<String> cacheElement2 = new CacheElement<>("testElement", 1);
        assertNotEquals(cacheElement1, cacheElement2);
    }

    @Test
    public void testEqualsWithNull() {
        CacheElement<String> cacheElement1 = new CacheElement<>("testElement", 0);
        CacheElement<String> cacheElement2 = new CacheElement<>(null, 0);
        assertNotEquals(cacheElement1, cacheElement2);
    }

}