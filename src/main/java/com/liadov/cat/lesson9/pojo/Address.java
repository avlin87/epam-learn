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

    public String getCountry() {
        log.trace("getCountry() method invoked");
        return country;
    }

    public void setCountry(String country) {
        log.trace("setCountry() method invoked with value = {}", country);
        this.country = country;
    }

    public String getState() {
        log.trace("getState() method invoked");
        return state;
    }

    public void setState(String state) {
        log.trace("setState() method invoked with value = {}", state);
        this.state = state;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
