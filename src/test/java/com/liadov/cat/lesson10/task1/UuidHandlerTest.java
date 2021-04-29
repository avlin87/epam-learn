package com.liadov.cat.lesson10.task1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UuidHandlerTest test for {@link UuidHandler}
 *
 * @author Aleksandr Liadov on 4/26/2021
 */
public class UuidHandlerTest {

    @Test
    public void generateCollectionAsRequestedAmount() {
        UuidHandler handler = new UuidHandler();
        int expectedResult = 10;

        List<String> stringList = handler.generateCollection(10);
        int actualResult = stringList.size();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void generateCollectionReturnNotNull() {
        UuidHandler handler = new UuidHandler();

        List<String> stringList = handler.generateCollection(0);

        assertNotNull(stringList);
    }

    @Test
    public void writeCollectionToFile() throws IOException {
        UuidHandler handler = new UuidHandler();
        Path path = Paths.get("testFile.txt");
        Files.deleteIfExists(path);
        List<String> stringList = List.of("999999999999", "9a9b9c9d9e9f9g9h9i9j9K9", "testRow", "999999999999");
        int expectedResult = 3;

        handler.writeCollectionToFile(path, stringList);

        int actualResult = handler.countElementsInFileWithSumOfDigitsGreaterHundred(path);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void writeCollectionToFileNotThrowingNullPointer() {
        UuidHandler handler = new UuidHandler();

        Executable executable = () -> handler.writeCollectionToFile(null, null);

        assertDoesNotThrow(executable);
    }

    @Test
    public void countElementsInFileWithSumOfDigitsGraterHundredNotThrowingNullPointer() {
        UuidHandler handler = new UuidHandler();

        Executable executable = () -> handler.countElementsInFileWithSumOfDigitsGreaterHundred(null);

        assertDoesNotThrow(executable);
    }

    @Test
    public void doomsDayReturnNotNull() {
        UuidHandler handler = new UuidHandler();

        LocalDate localDate = handler.doomsDay(0);

        assertNotNull(localDate);
    }
}