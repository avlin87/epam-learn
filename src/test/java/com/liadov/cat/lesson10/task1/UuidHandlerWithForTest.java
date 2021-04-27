package com.liadov.cat.lesson10.task1;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UuidHandlerWithForTest test for {@link UuidHandlerWithFor}
 *
 * @author Aleksandr Liadov on 4/26/2021
 */
public class UuidHandlerWithForTest {

    @Test
    public void generateCollectionAsRequestedAmount() {
        UuidHandlerWithFor handler = new UuidHandlerWithFor();
        int expectedResult = 10;

        List<String> stringList = handler.generateCollection(10);

        assertEquals(expectedResult, stringList.size());
    }

    @Test
    public void generateCollectionReturnNotNull() {
        UuidHandlerWithFor handler = new UuidHandlerWithFor();

        List<String> stringList = handler.generateCollection(0);

        assertNotNull(stringList);
    }

    @Test
    public void writeCollectionToFile() throws IOException {
        UuidHandlerWithFor handler = new UuidHandlerWithFor();
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
        UuidHandlerWithFor handler = new UuidHandlerWithFor();

        assertDoesNotThrow(() -> handler.writeCollectionToFile(null, null));
    }

    @Test
    public void countElementsInFileWithSumOfDigitsGraterHundredNotThrowingNullPointer() {
        UuidHandlerWithFor handler = new UuidHandlerWithFor();

        assertDoesNotThrow(() -> handler.countElementsInFileWithSumOfDigitsGreaterHundred(null));
    }

    @Test
    public void doomsDayReturnNotNull() {
        UuidHandlerWithFor handler = new UuidHandlerWithFor();

        LocalDate localDate = handler.doomsDay(0);

        assertNotNull(localDate);
    }
}