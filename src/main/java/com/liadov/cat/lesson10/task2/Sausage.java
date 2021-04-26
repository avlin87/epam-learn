package com.liadov.cat.lesson10.task2;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Sausage
 *
 * @author Aleksandr Liadov on 4/25/2021
 */
@Slf4j
@Data
public class Sausage {
    String type;
    int weight;
    long cost;

    public Sausage(String type, int weight, long cost) {
        this.type = type;
        this.weight = weight;
        this.cost = cost;
        log.trace("Sausage initiated: {}", this.toString());
    }

    @Override
    public String toString() {
        return "Sausage{" +
                "type='" + type + '\'' +
                ", weight=" + weight +
                ", cost=" + cost +
                '}';
    }
}
