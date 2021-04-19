package com.liadov.cat.lesson9.pojo;

import com.liadov.cat.lesson9.annotations.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bridge
 *
 * @author Aleksandr Liadov on 4/20/2021
 */
public class Bridge {
    private static final Logger log = LoggerFactory.getLogger(Book.class);

    @Value
    private int length;
    private int width;

    public Bridge() {
        log.info("Bridge object initialization");
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }


    public int getWidth() {
        return width;
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
