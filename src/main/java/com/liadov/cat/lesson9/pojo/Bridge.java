package com.liadov.cat.lesson9.pojo;

import com.liadov.cat.lesson9.annotations.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bridge - pojo class for test.
 * Has @Value annotation but missing @Entity annotation
 * IllegalStateException expected to be thrown during ReflectionHandler().isAnnotatedWithEntity()
 *
 * @author Aleksandr Liadov on 4/20/2021
 */
public class Bridge {
    private static final Logger log = LoggerFactory.getLogger(Bridge.class);

    @Value
    private int length;
    private int width;

    public Bridge() {
        log.info("Bridge object initialization");
    }

    @Value
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Bridge{" +
                "length=" + length +
                ", width=" + width +
                '}';
    }
}
