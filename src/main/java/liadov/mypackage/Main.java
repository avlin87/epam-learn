package liadov.mypackage;

import liadov.mypackage.lesson2.Cache;
import liadov.mypackage.lesson2.Storage;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        Cache<String> cache = new Cache<>(3);
        cache.add("one");
        cache.add("two");
        cache.add("three");
        cache.add("fore");

        Storage<Integer> storage = new Storage<>(new Integer[]{2,4});

        storage.add(2);
        storage.add(17);
        storage.add(99);
        storage.delete();
        storage.add(11);
        storage.add(81);
        storage.add(24);
        System.out.println(storage.getLast());
        System.out.println(storage.get(1));
        System.out.println(storage.get(1));
    }
}
