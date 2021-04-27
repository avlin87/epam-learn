package com.liadov.cat.lesson11.tasks1and2;

import com.liadov.cat.lesson11.tasks1and2.interfaces.MathRace;
import com.liadov.cat.lesson11.tasks1and2.problem.MathRaceCondition;
import com.liadov.cat.lesson11.tasks1and2.solution.MathRaceConditionSolved;
import lombok.extern.slf4j.Slf4j;

/**
 * RaceConditionExample - shows example of RaceCondition threads and threads with solved RaceCondition
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class RaceConditionExample {
    /**
     * Method starts Threads for objects with solved Race Condition
     */
    public void startNormalMath() {
        MathRaceConditionSolved mathRaceCondition = new MathRaceConditionSolved();
        startThreads(mathRaceCondition);
    }

    /**
     * Method starts Threads for objects with Race Condition problem
     */
    public void startRaceConditionMath() {
        MathRaceCondition mathRaceCondition = new MathRaceCondition();
        startThreads(mathRaceCondition);
    }

    private void startThreads(MathRace mathRace) {
        sleep(100);
        String className = mathRace.getClass().getSimpleName();
        log.info("{} started", className);
        Thread t1 = new Thread(mathRace, "Thread-1");
        Thread t2 = new Thread(mathRace, "Thread-2");
        Thread t3 = new Thread(mathRace, "Thread-3");
        t1.start();
        t2.start();
        t3.start();
        while (t1.isAlive() | t2.isAlive() | t3.isAlive()) {
            sleep(10);
        }
        log.info("{} finished\n", className);
    }

    private void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            log.error("sleep interrupted", e);
        }
    }
}
