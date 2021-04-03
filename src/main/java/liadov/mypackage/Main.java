package liadov.mypackage;

import liadov.mypackage.lesson2.Cache;
import liadov.mypackage.lesson2.Storage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        Cache<String> cache = new Cache<>(3);

        cache.add("one",1);
        cache.add("two",2);
        cache.add("three",3);
        cache.add("four",4);
        cache.delete("two");
        cache.clear();

        Storage<Integer> storage = new Storage<>(new Integer[]{2, 4});

        System.out.println("Creation via constructor: " + storage);
        storage.add(17);
        System.out.println("add(17): " + storage);
        storage.add(99);
        storage.add(14);
        storage.add(29);
        storage.delete();
        storage.getLast();
        storage.get(1);
        storage.get(1);
        storage.get(55);
        storage.get(0);
        storage.get(4);
        storage.delete();
        storage.clear();
    }
}
