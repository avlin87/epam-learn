package com.liadov.cat.lesson10.task2;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Sausage - pojo class for test.
 *
 * @author Aleksandr Liadov on 4/25/2021
 */
@Slf4j
@Data
public class Sausage {
    private String type;
    private int weight;
    private long cost;

    public Sausage(String type, int weight, long cost) {
        this.type = type;
        this.weight = weight;
        this.cost = cost;
        log.trace("Sausage initiated: {}", this.toString());
    }
}
