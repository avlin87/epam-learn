package com.liadov.cat.lesson9;

import com.liadov.cat.lesson9.exceptions.NoValueAnnotationException;
import com.liadov.cat.lesson9.pojo.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ReflectionHandlerTest
 *
 * @author Aleksandr Liadov on 4/20/2021
 */
public class ReflectionHandlerTest {

    @Test
    public void isAnnotatedWithEntityReturnTrue() {
        Human human = new Human();

        boolean actualResult = new ReflectionHandler().isAnnotatedWithEntity(human);

        assertTrue(actualResult);
    }

    @Test
    public void isAnnotatedWithEntityReturnFalse() {
        Address address = new Address();

        boolean actualResult = new ReflectionHandler().isAnnotatedWithEntity(address);

        assertFalse(actualResult);
    }

    @Test
    public void isAnnotatedWithEntityThrowNoValueAnnotationException() {
        Book book = new Book();
        ReflectionHandler handler = new ReflectionHandler();

        assertThrows(NoValueAnnotationException.class, () -> handler.isAnnotatedWithEntity(book));
    }

    @Test
    public void isAnnotatedWithEntityThrowIllegalStateException() {
        Bridge bridge = new Bridge();
        ReflectionHandler handler = new ReflectionHandler();

        assertThrows(IllegalStateException.class, () -> handler.isAnnotatedWithEntity(bridge));
    }

    @Test
    public void populateFieldsWithValuesFromAnnotation() {
        Human human = new Human();
        new ReflectionHandler().populateFieldsWithValues(human, false);

        String actualResult = human.getFirstName();

        assertEquals("Test First Name", actualResult);
    }

    @Test
    public void populateFieldsWithValuesFromFile() {
        Dog dog = new Dog();
        new ReflectionHandler().populateFieldsWithValues(dog, false);

        String actualResult = dog.getName();
        int intActualResult = dog.getAge();

        assertEquals("2name", actualResult);
        assertEquals(40, intActualResult);
    }

    @Test
    public void getMultipleObjectsFromFileNotThrowException() {
        ReflectionHandler handler = new ReflectionHandler();

        handler.getMultipleObjectsFromFile(NoConstructor.class);
    }

    private class NoConstructor {
        private NoConstructor() {
        }
    }
}