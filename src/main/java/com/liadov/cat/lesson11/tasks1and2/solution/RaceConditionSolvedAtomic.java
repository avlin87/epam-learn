package com.liadov.cat.lesson11.tasks1and2.solution;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * RaceConditionSolvedAtomic
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class RaceConditionSolvedAtomic implements Runnable {

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        AtomicInteger atomicValue = new AtomicInteger(0);
        log.info("[{}]: Atomic Value Increment = {}", threadName, atomicValue.incrementAndGet());

        log.info("[{}]: Atomic Value Decrement = {}", threadName, atomicValue.decrementAndGet());
    }
}
