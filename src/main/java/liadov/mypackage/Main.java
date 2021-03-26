package liadov.mypackage;

import liadov.mypackage.lesson2.Cache;
import liadov.mypackage.lesson2.Storage;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        System.out.println("Cache:");

        Cache<String> cache = new Cache<>(3);

        cache.add("one",1);
        System.out.println("add(\"one\"), result: " + cache);
        cache.add("two",2);
        System.out.println("add(\"two\"), result: " + cache);
        cache.add("three",3);
        System.out.println("add(\"three\"), result: " + cache);
        cache.add("four",4);
        System.out.println("add(\"four\"), result: " + cache);
        cache.delete("two");
        System.out.println("delete(\"two\"), result: " + cache);
        System.out.println("isPresent(\"four\"): " + cache.isPresent("four"));
        System.out.println("get(\"3\"): \"" + cache.get(3) + "\" " + cache);
        cache.clear();
        System.out.println("cache.clear(), result: " + cache);

        System.out.println("\nStorage");

        Storage<Integer> storage = new Storage<>(new Integer[]{2, 4});

        System.out.println("Creation via constructor: " + storage);
        storage.add(17);
        System.out.println("add(17): " + storage);
        storage.add(99);
        System.out.println("add(99): " + storage);
        storage.delete();
        System.out.println("delete(): " + storage);
        System.out.println("storage.getLast(): " + storage.getLast());
        System.out.println("storage.get(1): " + storage.get(1) + " - " + storage);
        System.out.println("storage.get(2): " + storage.get(2) + " - " + storage);
        System.out.println("storage.get(0): " + storage.get(0) + " - " + storage);
        System.out.println("storage.get(1): " + storage.get(1) + " - " + storage);
        storage.delete();
        System.out.println("delete(): " + storage);
        storage.clear();
        System.out.println("storage.clear: " + storage);
    }
}
