package com.liadov.cat.lesson11.tasks1and2;

import com.liadov.cat.lesson11.tasks1and2.interfaces.Puppy;
import com.liadov.cat.lesson11.tasks1and2.problem.PuppyWithDeadLock;
import com.liadov.cat.lesson11.tasks1and2.solution.PuppyNormal;
import lombok.extern.slf4j.Slf4j;

/**
 * DeadLockTest - shows example of DeadLock threads and threads with solved DeadLock
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class DeadLockExample {

    /**
     * Method start Threads for objects with solved DeadLock
     */
    public void startNormalPuppies() {
        Puppy arnold = new PuppyNormal("Arnold");
        Puppy kevin = new PuppyNormal("Kevin");

        startThreadsTest(arnold, kevin);
    }

    /**
     * Method start Threads for objects with DeadLock problem
     */
    public void startDeadLock() {
        Puppy gerald = new PuppyWithDeadLock("Gerald");
        Puppy leo = new PuppyWithDeadLock("Leo");

        startThreadsTest(gerald, leo);
    }

    private void startThreadsTest(Puppy puppy1, Puppy puppy2) {
        Thread puppy1Thread = new Thread(() -> puppy1.playWithPuppy(puppy2));
        Thread puppy2Thread = new Thread(() -> puppy2.playWithPuppy(puppy1));
        puppy1Thread.start();
        puppy2Thread.start();

        String puppy1Name = puppy1.getName();
        String puppy2Name = puppy2.getName();
        String puppyType = puppy1.getClass().getSimpleName();
        try {
            Thread.sleep(2000);
            if (puppy1Thread.isAlive() && puppy2Thread.isAlive()) {
                log.info("DeadLock reproduced for puppies: {} and {}, with type of puppy = {}\n", puppy1Name, puppy2Name, puppyType);
            } else {
                log.info("Process finished successfully for puppies: {} and {}, with type of puppy = {}\n", puppy1Name, puppy2Name, puppyType);
            }
        } catch (InterruptedException e) {
            log.error("Sleep interrupted", e);
        }
    }
}
