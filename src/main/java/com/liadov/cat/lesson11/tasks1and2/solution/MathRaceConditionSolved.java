package com.liadov.cat.lesson11.tasks1and2.solution;

import com.liadov.cat.lesson11.tasks1and2.interfaces.MathRace;
import lombok.extern.slf4j.Slf4j;

/**
 * MathRaceConditionSolved - class example with solution for Race Condition problem
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class MathRaceConditionSolved implements Runnable, MathRace {
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
        synchronized (this) {
            String threadName = Thread.currentThread().getName();
            this.increment();
            int value = this.getValue();
            log.info("[{}]: value Increment = {}", threadName, value);

            this.decrement();
            value = this.getValue();
            log.info("[{}]: value Decrement = {}", threadName, value);
        }
    }
}
