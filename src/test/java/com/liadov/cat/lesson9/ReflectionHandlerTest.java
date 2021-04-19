package com.liadov.cat.lesson9;

import com.liadov.cat.lesson9.exceptions.NoValueAnnotationException;
import com.liadov.cat.lesson9.pojo.Address;
import com.liadov.cat.lesson9.pojo.Book;
import com.liadov.cat.lesson9.pojo.Bridge;
import com.liadov.cat.lesson9.pojo.Human;
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
    public void populateFieldsWithValuesFromAnnotation(){
        Human human = new Human();
        new ReflectionHandler().populateFieldsWithValues(human);

        String actualResult = human.getFirstName();

        assertEquals("Test First Name", actualResult);
    }
}