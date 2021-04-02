package liadov.mypackage;

import liadov.mypackage.lesson2.Cache;
import liadov.mypackage.lesson2.CacheElement;
import liadov.mypackage.lesson2.Storage;
import liadov.mypackage.lesson4.MyCheckedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) throws MyCheckedException {
        System.out.println("Hello World");

        /*String equalsCheck = "\nCheck equals() Method of CacheElement class:" +
                "\n first element: %s" +
                "\n second element: %s" +
                "\n result: %s" +
                "\n";

        CacheElement<String> cacheElement1 = new CacheElement<>(null,1);
        CacheElement<String> cacheElement2 = new CacheElement<>(null,1);
        System.out.printf(equalsCheck, cacheElement1,cacheElement2,cacheElement1.equals(cacheElement2));
        System.out.printf(equalsCheck, cacheElement1,null,cacheElement1.equals(null));

        System.out.println("\nCache:");

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
        System.out.println("cache.clear(), result: " + cache);*/

        System.out.println("\nStorage");

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
