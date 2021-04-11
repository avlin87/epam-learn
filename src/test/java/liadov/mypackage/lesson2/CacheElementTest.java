package liadov.mypackage.lesson2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheElementTest {

    @Test
    void testEqualsShouldReturnTrue() {
        CacheElement<String> cacheElement1 = new CacheElement<>("testElement", 1);
        CacheElement<String> cacheElement2 = new CacheElement<>("testElement", 1);
        assertTrue(cacheElement1.equals(cacheElement2));
    }

    @Test
    void testEqualsShouldReturnFalse() {
        CacheElement<String> cacheElement1 = new CacheElement<>("testElement", 0);
        CacheElement<String> cacheElement2 = new CacheElement<>("testElement", 1);
        assertFalse(cacheElement1.equals(cacheElement2));
    }

    @Test
    void testEqualsWithNull() {
        CacheElement<String> cacheElement1 = new CacheElement<>("testElement", 0);
        CacheElement<String> cacheElement2 = new CacheElement<>(null, 0);
        assertFalse(cacheElement1.equals(cacheElement2));
    }

}