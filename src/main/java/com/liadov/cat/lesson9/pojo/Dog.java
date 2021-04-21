package com.liadov.cat.lesson9.pojo;

import com.liadov.cat.lesson9.annotations.Entity;
import com.liadov.cat.lesson9.annotations.Value;

/**
 * Dog
 *
 * @author Aleksandr Liadov on 4/22/2021
 */
@Entity
public class Dog {

    @Value(fileName = "src/main/resources/sourceFile.txt")
    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Value(fileName = "src/main/resources/sourceFile.txt")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "age=" + age +
                ", name=" + name +
                '}';
    }
}
