package com.liadov.cat.lesson10.task1;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * UuidHandlerWithFor - class for operating with UUID collections (with for/foreach)
 *
 * @author Aleksandr Liadov on 4/25/2021
 */
@Slf4j
public class UuidHandlerWithFor {
    private Path defaultFilePath = Paths.get("src/main/resources/defaultFile.txt");

    /**
     * Method generate collection with randomUUID elements and size according to provided variable amountOfObjects
     *
     * @param amountOfObjects int size of collection to be generated
     * @return List<String> collection
     */
    public List<String> generateCollection(int amountOfObjects) {
        log.debug("initiated generation with amount of elements = {}", amountOfObjects);
        List<String> stringList = Stream.empty().map(String::valueOf).collect(Collectors.toList());

        for (int i = 0; i < amountOfObjects; i++) {
            stringList.add(UUID.randomUUID().toString());
        }

        log.trace("amount of generated objects = {}", stringList.size());
        return stringList;
    }

    /**
     * Method write target List<String> collection to target file path
     *
     * @param path Path target file. Method use defaultFilePath in case provided path is null
     * @param list List<String> target collection to write in file
     */
    public void writeCollectionToFile(Path path, List<String> list) {
        Optional<Path> optionalPath = Optional.ofNullable(path);
        Optional<List<String>> optionalStringList = Optional.ofNullable(list);
        log.trace("Path = {}, list size = {}", optionalPath, optionalStringList.orElse(List.of()).size());
        if (optionalStringList.isPresent()) {
            try {
                Files.write(optionalPath.orElse(defaultFilePath), list);
                log.debug("write operation executed successfully");
            } catch (IOException e) {
                log.error("File operation FAILED", e);
            }
        }
    }

    /**
     * Method reads target file and return number of rows with sum of digit symbols grater that 100
     *
     * @param path Path target file
     * @return int number of rows with sum of digit symbols grater that 100
     */
    public int countElementsInFileWithSumOfDigitsGraterHundred(Path path) {
        List<String> elementsWithSumOfDigitsGraterHundred = Stream.empty().map(String::valueOf).collect(Collectors.toList());
        Optional<Path> optionalPath = Optional.ofNullable(path);
        log.trace("initiated count of elements that have sum of all digits grater than 100. Path = {}", optionalPath);
        if (optionalPath.isPresent()) {
            List<String> tempElements = List.of();
            try {
                tempElements = Files.lines(path).collect(Collectors.toList());
            } catch (IOException e) {
                log.error("Error", e);
            }

            for (String element : tempElements) {
                String[] elementSymbol = element.split("");
                int sum = 0;
                for (String s : elementSymbol) {
                    if (s.matches("\\d+")) {
                        sum += Integer.parseInt(s);
                        if (sum > 100) {
                            break;
                        }
                    }
                }
                if (sum > 100) {
                    elementsWithSumOfDigitsGraterHundred.add(element);
                }
            }

        }
        log.debug("count elements with sum of digits grater then 100 = {}", elementsWithSumOfDigitsGraterHundred.size());
        return elementsWithSumOfDigitsGraterHundred.size();
    }

    /**
     * Method calculate dooms day based on provided number
     *
     * @param count int provided number
     * @return LocalDate calculated dooms day value
     */
    public LocalDate doomsDay(int count) {
        int months = count / 100;
        int days = count - months * 100;
        log.debug("input = {}. dooms day calculation: now = {}, + months = {}, + days = {}", count, LocalDate.now(), months, days);
        return LocalDate.now().plusMonths(months).plusDays(days);
    }
}
