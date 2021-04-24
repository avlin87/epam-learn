package com.liadov.cat;

import com.liadov.cat.lesson9.ReflectionHandler;
import com.liadov.cat.lesson9.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
        reflectionHandler.populateFieldsWithValues(dog, false);

        Human human = new Human();
        reflectionHandler.populateFieldsWithValues(human, false);

        Address address = new Address();
        reflectionHandler.populateFieldsWithValues(address, false);

        Book book = new Book();
        reflectionHandler.populateFieldsWithValues(book, false);

        Bridge bridge = new Bridge();
        reflectionHandler.populateFieldsWithValues(bridge, false);

        List objectsFromFile = reflectionHandler.getMultipleObjectsFromFile(Dog.class);

        log.trace("\nvalues are populated:");
        log.trace("from file: {}", dog);
        log.trace("from annotations: {}", human);
        log.trace("no annotations, values empty: {}", address);
        log.trace("NoValueAnnotationException: {}", book);
        log.trace("IllegalStateException: {}", bridge);

        for (Object createdObject : objectsFromFile) {
            log.trace("object created from file {}", createdObject);
        }
    }
}