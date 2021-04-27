package com.liadov.cat.lesson10.task1;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * UUIDHandler - class for operating with UUID collections
 *
 * @author Aleksandr Liadov on 4/25/2021
 */
@Slf4j
public class UuidHandler {
    private Path defaultFilePath = Paths.get("src/main/resources/defaultFile.txt");

    /**
     * Method generate collection with randomUUID elements and size according to provided variable amountOfObjects
     *
     * @param amountOfObjects int size of collection to be generated
     * @return List<String> collection
     */
    public List<String> generateCollection(int amountOfObjects) {
        log.debug("initiated generation with amount of elements = {}", amountOfObjects);
        List<String> uuidCollection = Stream.generate((UUID::randomUUID))
                .map(UUID::toString)
                .limit(amountOfObjects)
                .collect(Collectors.toList());
        Optional<List<String>> optionalStringList = Optional.ofNullable(uuidCollection);
        List<String> resultUuidCollection = optionalStringList.orElse(List.of());
        log.trace("amount of generated objects = {}", resultUuidCollection.size());
        return resultUuidCollection;
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

        optionalStringList.ifPresent((x) -> {
            try {
                Files.write(optionalPath.orElse(defaultFilePath), list);
                log.debug("write operation executed successfully");
            } catch (IOException e) {
                log.error("File operation FAILED", e);
            }
        });
    }

    /**
     * Method reads target file and return number of rows with sum of digit symbols greater that 100
     *
     * @param path Path target file
     * @return int number of rows with sum of digit symbols greater that 100
     */
    public int countElementsInFileWithSumOfDigitsGreaterHundred(Path path) {
        List<String> elementsWithSumOfDigitsGreaterHundred = List.of();
        Optional<Path> optionalPath = Optional.ofNullable(path);
        log.trace("initiated count of elements that have sum of all digits greater than 100. Path = {}", optionalPath);
        if (optionalPath.isPresent()) {
            try {
                elementsWithSumOfDigitsGreaterHundred = Files.lines(path)
                        .filter((str) -> checkSumOfDigitsGreaterThanHundred(str))
                        .collect(Collectors.toList());
            } catch (IOException e) {
                log.error("Error", e);
            }
        }
        log.debug("count elements with sum of digits greater then 100 = {}", elementsWithSumOfDigitsGreaterHundred.size());
        return elementsWithSumOfDigitsGreaterHundred.size();
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

    private boolean checkSumOfDigitsGreaterThanHundred(String str) {
        String[] symbols = str.split("");
        boolean checkResult = Arrays.stream(symbols)
                .filter(s -> s.matches("\\d+"))
                .mapToInt(Integer::valueOf)
                .sum() > 100;
        return checkResult;
    }
}
