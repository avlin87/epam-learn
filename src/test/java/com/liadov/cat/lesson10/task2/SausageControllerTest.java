package com.liadov.cat.lesson10.task2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SausageControllerTest test for {@link SausageController}
 *
 * @author Aleksandr Liadov on 4/26/2021
 */
public class SausageControllerTest {

    @Test
    public void writeListToFileCorrectNumberOfObjects() throws IOException {
        Path path = Paths.get("testFile.txt");
        Files.deleteIfExists(path);
        SausageController sausageController = new SausageController();
        Sausage one = new Sausage("one", 1, 1);
        Sausage two = new Sausage("two", 2, 2);
        Sausage three = new Sausage("three", 3, 3);
        List<Sausage> list = List.of(one, two, three);
        int expectedResult = list.size();

        sausageController.writeListToFile(path, list);

        List<Sausage> sausageList = sausageController.readObjectsFromFile(path);
        int actualResult = sausageList.size();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void writeListToFileNotThrowingNullPointer() {
        SausageController sausageController = new SausageController();

        Executable executable = () -> sausageController.writeListToFile(null, null);

        assertDoesNotThrow(executable);
    }

    @Test
    public void readObjectsFromFileSameAsWrittenToFile() throws IOException {
        Path path = Paths.get("testFile.txt");
        Files.deleteIfExists(path);
        SausageController sausageController = new SausageController();
        Sausage one = new Sausage("one", 1, 1);
        Sausage two = new Sausage("two", 2, 2);
        Sausage three = new Sausage("three", 3, 3);
        Sausage four = new Sausage("four", 4, 4);
        List<Sausage> expectedList = List.of(one, two, three, four);

        sausageController.writeListToFile(path, expectedList);

        List<Sausage> actualSausageList = sausageController.readObjectsFromFile(path);
        boolean result = expectedList.containsAll(actualSausageList);
        assertTrue(result);
    }

    @Test
    public void readObjectsFromFileNotThrowingNullPointer() {
        SausageController sausageController = new SausageController();

        Executable executable = () -> sausageController.readObjectsFromFile(null);

        assertDoesNotThrow(executable);
    }

}