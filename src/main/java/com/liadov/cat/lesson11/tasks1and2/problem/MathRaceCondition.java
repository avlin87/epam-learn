package com.liadov.cat.lesson11.tasks1and2.problem;

import com.liadov.cat.lesson11.tasks1and2.interfaces.MathRace;
import lombok.extern.slf4j.Slf4j;

/**
 * MathRaceCondition - class for demonstrating java Race Condition
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class MathRaceCondition implements Runnable, MathRace {
    private int a = 0;

    public void decrement() {
        a--;
    }

    public void increment() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            log.error("Sleep interrupted", e);
        }
        a++;
    }

    public int getValue() {
        return a;
    }


    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        this.increment();
        int value = this.getValue();
        log.info("[{}]: value Increment = {}", threadName, value);

        this.decrement();
        value = this.getValue();
        log.info("[{}]: value Decrement = {}", threadName, value);
    }
}