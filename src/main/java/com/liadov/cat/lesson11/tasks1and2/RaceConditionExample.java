package com.liadov.cat.lesson11.tasks1and2;

import com.liadov.cat.lesson11.tasks1and2.problem.MathRaceCondition;
import com.liadov.cat.lesson11.tasks1and2.solution.MathRaceConditionSolved;
import com.liadov.cat.lesson11.tasks1and2.solution.RaceConditionSolvedAtomic;
import lombok.extern.slf4j.Slf4j;

/**
 * RaceConditionExample - shows example of RaceCondition threads and threads with solved RaceCondition
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class RaceConditionExample {
    /**
     * Method starts Threads for objects with solved Race Condition with synchronized
     */
    public void startNormalMath() {
        var mathRaceCondition = new MathRaceConditionSolved();
        startThreads(mathRaceCondition);
    }

    /**
     * Method starts Threads for objects with solved Race Condition with AtomicInteger
     */
    public void startNormalAtomicMath() {
        var raceConditionSolvedAtomic = new RaceConditionSolvedAtomic();
        startThreads(raceConditionSolvedAtomic);
    }

    /**
     * Method starts Threads for objects with Race Condition problem
     */
    public void startRaceConditionMath() {
        var mathRaceCondition = new MathRaceCondition();
        startThreads(mathRaceCondition);
    }

    private void startThreads(Runnable mathRace) {
        sleep(100);
        String className = mathRace.getClass().getSimpleName();
        log.info("{} started", className);
        var raceThread1 = new Thread(mathRace, "Thread-1");
        var raceThread2 = new Thread(mathRace, "Thread-2");
        var raceThread3 = new Thread(mathRace, "Thread-3");
        raceThread1.start();
        raceThread2.start();
        raceThread3.start();
        while (raceThread1.isAlive() || raceThread2.isAlive() || raceThread3.isAlive()) {
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
