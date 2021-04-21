package com.liadov.cat.lesson9;

import com.liadov.cat.lesson9.pojo.Human;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FileOperationsTest
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

        Map<String, String> actualResult = fileOperations.readValuesFromFile(reader);

        assertTrue(expectedResult.equals(actualResult));
    }

    @Test
    public void readValuesFromFileNotReturningNull() throws IOException {
        FileOperations fileOperations = new FileOperations();

        Map<String, String> actualResult = fileOperations.readValuesFromFile(null);

        assertNotNull(actualResult);
    }

    @Test
    public void getValueFromFileTest() throws IOException, NoSuchFieldException {
        FileOperations fileOperations = new FileOperations();
        Human human = new Human();
        File file = new File("test.txt");
        file.createNewFile();
        String expectedResult = "19";
        try (FileWriter myWriter = new FileWriter(file)) {
            myWriter.write("age=" + expectedResult + "\nname=2name");
        }

        String actualResult = fileOperations.getValueFromFile(file, human.getClass().getDeclaredField("age"));
        file.deleteOnExit();

        assertEquals(expectedResult, actualResult);
    }

}