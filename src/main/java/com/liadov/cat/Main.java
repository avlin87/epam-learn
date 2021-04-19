package com.liadov.cat;

import com.liadov.cat.lesson2.Storage;
import com.liadov.cat.exceptions.ElementNotFoundByIndex;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        Storage<String> stringStorage = new Storage<>(new String[]{"one", "two"});
        stringStorage.add("first row");
        stringStorage.add("second row");
        stringStorage.delete();
        stringStorage.get(0);
        stringStorage.clear();
        try {
            stringStorage.get(0);
        } catch (ElementNotFoundByIndex e) {
            log.error("Failed to get storage Element", e);
        }
        stringStorage.add("first row");
        stringStorage.add("second row");
        stringStorage.getLast();
    }

}