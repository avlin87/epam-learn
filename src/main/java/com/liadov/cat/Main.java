package com.liadov.cat;

import com.liadov.cat.lesson10.task1.UuidHandler;
import com.liadov.cat.lesson10.task1.UuidHandlerWithFor;
import com.liadov.cat.lesson10.task2.Sausage;
import com.liadov.cat.lesson10.task2.SausageController;
import com.liadov.cat.lesson10.task2.SausageControllerWithFor;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args) {
        task01Example();
        task01ExampleWithFor();
        task02Example();
        task02ExampleWithFor();
    }

    private static void task01Example() {
        UuidHandler uuidHandler = new UuidHandler();
        Path path = Paths.get("src/main/resources/uuidFile.txt");
        int amountOfObjects = 10000;
        List<String> list = uuidHandler.generateCollection(amountOfObjects);

        uuidHandler.writeCollectionToFile(path, list);
        int count = uuidHandler.countElementsInFileWithSumOfDigitsGraterHundred(path);
        log.info("count of elements from file with sum of all digits grater than hundred = {}", count);

        LocalDate localDoomsDayDate = uuidHandler.doomsDay(count);
        log.info("local dooms day date = {}", localDoomsDayDate);
    }

    private static void task01ExampleWithFor() {
        UuidHandlerWithFor uuidHandlerWithFor = new UuidHandlerWithFor();
        Path path = Paths.get("src/main/resources/uuidFileWithFor.txt");
        int amountOfObjects = 10000;
        List<String> list = uuidHandlerWithFor.generateCollection(amountOfObjects);


        uuidHandlerWithFor.writeCollectionToFile(path, list);
        int count = uuidHandlerWithFor.countElementsInFileWithSumOfDigitsGraterHundred(path);
        log.info("count of elements from file with sum of all digits grater than hundred = {}", count);

        LocalDate localDoomsDayDate = uuidHandlerWithFor.doomsDay(count);
        log.info("local dooms day date = {}", localDoomsDayDate);
    }

    private static void task02Example() {
        SausageController sausageController = new SausageController();
        Path path = Paths.get("src/main/resources/sausageFile.txt");
        List<Sausage> sausageList = List.of(new Sausage("one", 11, 301), new Sausage("two", 12, 302), new Sausage("three", 13, 303));

        sausageController.writeListToFile(path, sausageList);
        log.trace("elements written to file: {}", sausageList);

        List<Sausage> sausagesFromFile = sausageController.readObjectsFromFile(path);
        log.trace("sausage elements received from file: {}", sausagesFromFile);
    }

    private static void task02ExampleWithFor() {
        SausageControllerWithFor sausageControllerWithFor = new SausageControllerWithFor();
        Path path = Paths.get("src/main/resources/sausageFileWithFor.txt");
        List<Sausage> sausageList = List.of(new Sausage("one", 11, 301), new Sausage("two", 12, 302), new Sausage("three", 13, 303));

        sausageControllerWithFor.writeListToFile(path, sausageList);
        log.trace("elements written to file: {}", sausageList);

        List<Sausage> sausagesFromFile = sausageControllerWithFor.readObjectsFromFile(path);
        log.trace("sausage elements received from file: {}", sausagesFromFile);
    }
}