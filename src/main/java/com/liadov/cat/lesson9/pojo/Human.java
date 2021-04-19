package com.liadov.cat.lesson9.pojo;

import com.liadov.cat.lesson9.annotations.Entity;
import com.liadov.cat.lesson9.annotations.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Human
 *
 * @author Alexandr Liadov on 4/19/2021
 */
@Entity
public class Human {
    private static final Logger log = LoggerFactory.getLogger(Human.class);

    @Value("One")
    private int age;
    private String firstName;
    private String lastName;

    public Human() {
        log.info("Human object initialization");
    }

    public String getFirstName() {
        log.trace("getFirstName() method invoked");
        return firstName;
    }

    @Value("Test First Name")
    public void setFirstName(String firstName) {
        log.trace("setFirstName() method invoked with value = {}", firstName);
        this.firstName = firstName;
    }

    public String getLastName() {
        log.trace("getLastName() method invoked");
        return lastName;
    }

    @Value("One")
    public void setLastName(String lastName) {
        log.trace("setLastName() method invoked with value = {}", lastName);
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Human{" +
                "age=" + age +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
