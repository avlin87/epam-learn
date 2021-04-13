package liadov.mypackage.lesson2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CacheElementTest {

    @Test
    void testEqualsShouldReturnTrue() {
        CacheElement<String> cacheElement1 = new CacheElement<>("testElement", 1);
        CacheElement<String> cacheElement2 = new CacheElement<>("testElement", 1);
        assertEquals(cacheElement1, cacheElement2);
    }

    @Test
    void testEqualsShouldReturnFalse() {
        CacheElement<String> cacheElement1 = new CacheElement<>("testElement", 0);
        CacheElement<String> cacheElement2 = new CacheElement<>("testElement", 1);
        assertNotEquals(cacheElement1, cacheElement2);
    }

    @Test
    void testEqualsWithNull() {
        CacheElement<String> cacheElement1 = new CacheElement<>("testElement", 0);
        CacheElement<String> cacheElement2 = new CacheElement<>(null, 0);
        assertNotEquals(cacheElement1, cacheElement2);
    }

}