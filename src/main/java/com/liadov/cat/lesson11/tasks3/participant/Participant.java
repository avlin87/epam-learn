package com.liadov.cat.lesson11.tasks3.participant;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Participant
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public abstract class Participant {

    /**
     * Method update name of current Thread with accordance to called class name
     */
    protected void setCurrentThreadName() {
        String threadName = Thread.currentThread().getName();
        String newName = this.getClass().getSimpleName();
        threadName = threadName.replace("Thread", newName);
        Thread.currentThread().setName(threadName);
    }

    /**
     * Method send Thread to sleep random number of seconds between minSeconds and maxSeconds
     *
     * @param minSeconds int minimum number of seconds
     * @param maxSeconds int maximum number of seconds
     */
    protected void sleep(int minSeconds, int maxSeconds) {
        String threadName = Thread.currentThread().getName();
        int sleepTime = ThreadLocalRandom.current().nextInt(minSeconds, maxSeconds + 1);
        log.trace("{} sleep seconds: {}", threadName, sleepTime);
        try {
            int sleepMilliSeconds = sleepTime * 1000;
            Thread.sleep(sleepMilliSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.info("{} interrupted", threadName);
        }
    }
}
