package com.liadov.cat.lesson9.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Address
 *
 * @author Aleksandr Liadov on 4/20/2021
 */
public class Address {
    private static final Logger log = LoggerFactory.getLogger(Address.class);

    private String country;
    private String state;

    public Address() {
        log.info("Address object initialization");
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
