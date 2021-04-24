package com.liadov.cat.lesson9.pojo;

import com.liadov.cat.lesson9.annotations.Entity;
import com.liadov.cat.lesson9.annotations.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dog - pojo class for test.
 * Class has @Entity and @Value annotations.
 * Value annotation setting values from external file.
 *
 * @author Aleksandr Liadov on 4/22/2021
 */
@Entity
public class Dog {
    private static final Logger log = LoggerFactory.getLogger(Dog.class);

    @Value(fileVariable = "number")
    private int age;
    private String name;
    @Value(fileVariable = "owner")
    private String owner;

    public Dog() {
        log.info("Dog object initialization");
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Value(fileVariable = "name")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
