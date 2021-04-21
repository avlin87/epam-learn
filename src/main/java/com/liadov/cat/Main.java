package com.liadov.cat;

import com.liadov.cat.lesson9.ReflectionHandler;
import com.liadov.cat.lesson9.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main
 *
 * @author Alexandr Liadov on 4/20/2021
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final ReflectionHandler reflectionHandler = new ReflectionHandler();

    public static void main(String[] args) {
        reflectionExample();
    }

    private static void reflectionExample() {
        Dog dog = new Dog();
        Human human = new Human();
        Address address = new Address();
        Book book = new Book();
        Bridge bridge = new Bridge();

        reflectionHandler.populateFieldsWithValues(dog);
        reflectionHandler.populateFieldsWithValues(human);
        reflectionHandler.populateFieldsWithValues(address);
        reflectionHandler.populateFieldsWithValues(book);
        reflectionHandler.populateFieldsWithValues(bridge);

        log.info("\n\nvalues are populated:\n \n{} - from file\n{} - from annotations\n{} - no annotations\n{} - NoValueAnnotationException \n{} - IllegalStateException", dog, human, address, book, bridge);
    }
}