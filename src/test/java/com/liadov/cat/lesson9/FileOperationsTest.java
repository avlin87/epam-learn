package com.liadov.cat.lesson9;

import com.liadov.cat.lesson9.exceptions.NoValueAnnotationException;
import com.liadov.cat.lesson9.pojo.Dog;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FileOperationsTest test for {@link FileOperations}
 *
 * @author Aleksandr Liadov on 4/21/2021
 */
public class FileOperationsTest {

    @Test
    public void readValuesFromFileReturnPopulatedMap() {
        FileOperations fileOperations = new FileOperations();
        BufferedReader reader = new BufferedReader(new CharArrayReader("age=20\nname=2name\n".toCharArray()));
        Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put("AGE", "20");
        expectedResult.put("NAME", "2name");

        Map<String, String> actualResult = fileOperations.readValuesFromFile(reader).poll();

        assertTrue(expectedResult.equals(actualResult));
    }

    @Test
    public void readValuesFromFileNotReturningNull() {
        FileOperations fileOperations = new FileOperations();

        Queue<Map<String, String>> actualResult = fileOperations.readValuesFromFile(null);

        assertNotNull(actualResult);
    }

    @Test
    public void getValueFromFileTest() throws IOException, NoSuchFieldException {
        FileOperations fileOperations = new FileOperations();
        Dog dog = new Dog();
        String expectedResult = "40";

        String actualResult = fileOperations.getValueFromFile(dog.getClass().getDeclaredField("age"), dog, false);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void isFileAvailableReturnTrue() throws IOException {
        FileOperations fileOperations = new FileOperations();
        File file = new File("text.txt");
        file.createNewFile();

        boolean actualResult = fileOperations.isFileAvailable(false, file);

        assertTrue(actualResult);
        file.deleteOnExit();
    }

    @Test
    public void isFileAvailableReturnFalse() throws IOException {
        FileOperations fileOperations = new FileOperations();
        File file = new File("text.txt");
        file.deleteOnExit();

        boolean actualResult = fileOperations.isFileAvailable(false, file);

        assertFalse(actualResult);
    }

    @Test
    public void getAmountOfObjectsToBeCreatedReturnFive(){
        FileOperations fileOperations = new FileOperations();

        int actualResult = fileOperations.getAmountOfObjectsToBeCreated();

        assertEquals(5,actualResult);
    }
}