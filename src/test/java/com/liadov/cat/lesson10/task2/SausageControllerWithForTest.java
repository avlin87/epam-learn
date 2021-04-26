package com.liadov.cat.lesson10.task2;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SausageControllerWithForTest test for {@link SausageControllerWithFor}
 *
 * @author Aleksandr Liadov on 4/26/2021
 */
public class SausageControllerWithForTest {

    @Test
    public void writeListToFileCorrectNumberOfObjects() throws IOException {
        Path path = Paths.get("testFile.txt");
        Files.deleteIfExists(path);
        SausageControllerWithFor sausageController = new SausageControllerWithFor();
        List<Sausage> list = List.of(new Sausage("one", 1, 1), new Sausage("two", 2, 2), new Sausage("three", 3, 3));

        sausageController.writeListToFile(path, list);

        assertEquals(list.size(), sausageController.readObjectsFromFile(path).size());
    }

    @Test
    public void writeListToFileNotThrowingNullPointer() {
        SausageControllerWithFor sausageController = new SausageControllerWithFor();

        assertDoesNotThrow(() -> sausageController.writeListToFile(null, null));
    }

    @Test
    public void readObjectsFromFileSameAsWrittenToFile() throws IOException {
        Path path = Paths.get("testFile.txt");
        Files.deleteIfExists(path);
        SausageControllerWithFor sausageController = new SausageControllerWithFor();
        List<Sausage> list = List.of(new Sausage("one", 1, 1), new Sausage("two", 2, 2), new Sausage("three", 3, 3), new Sausage("four", 4, 4));

        sausageController.writeListToFile(path, list);

        assertTrue(list.containsAll(sausageController.readObjectsFromFile(path)));
    }

    @Test
    public void readObjectsFromFileNotThrowingNullPointer() throws IOException {
        SausageControllerWithFor sausageController = new SausageControllerWithFor();

        assertDoesNotThrow(() -> sausageController.readObjectsFromFile(null));
    }

}