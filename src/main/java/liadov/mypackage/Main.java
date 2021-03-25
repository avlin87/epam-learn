package liadov.mypackage;

import liadov.mypackage.lesson2.Cache;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        Cache<String> cache = new Cache<>(3);
        cache.add("one");
        cache.add("two");
        cache.add("three");
        cache.add("fore");
        cache.clear();
        System.out.println("очищен");
        cache.add("one");
        cache.add("two");
        cache.add("three");
        cache.add("fore");

        System.out.println(cache.get("two"));
        System.out.println(cache.get("three"));
        System.out.println(cache.get("one"));

        System.out.println(cache.isPresent("three"));
        cache.delete("three");
        System.out.println(cache.isPresent("three"));
        cache.delete("fore");
        cache.delete("two");
        cache.delete("two");

    }
}
