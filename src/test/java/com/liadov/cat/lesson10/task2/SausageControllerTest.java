package com.liadov.cat.lesson10.task2;

import org.junit.jupiter.api.Test;

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
        List<Sausage> list = List.of(new Sausage("one", 1, 1), new Sausage("two", 2, 2), new Sausage("three", 3, 3));

        sausageController.writeListToFile(path, list);

        assertEquals(list.size(), sausageController.readObjectsFromFile(path).size());
    }

    @Test
    public void writeListToFileNotThrowingNullPointer() {
        SausageController sausageController = new SausageController();

        assertDoesNotThrow(() -> sausageController.writeListToFile(null, null));
    }

    @Test
    public void readObjectsFromFileSameAsWrittenToFile() throws IOException {
        Path path = Paths.get("testFile.txt");
        Files.deleteIfExists(path);
        SausageController sausageController = new SausageController();
        List<Sausage> list = List.of(new Sausage("one", 1, 1), new Sausage("two", 2, 2), new Sausage("three", 3, 3), new Sausage("four", 4, 4));

        sausageController.writeListToFile(path, list);

        assertTrue(list.containsAll(sausageController.readObjectsFromFile(path)));
    }

    @Test
    public void readObjectsFromFileNotThrowingNullPointer() throws IOException {
        SausageController sausageController = new SausageController();

        assertDoesNotThrow(() -> sausageController.readObjectsFromFile(null));
    }

}