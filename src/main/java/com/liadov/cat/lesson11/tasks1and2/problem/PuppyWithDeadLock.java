package com.liadov.cat.lesson11.tasks1and2.problem;

import com.liadov.cat.lesson11.tasks1and2.interfaces.Puppy;
import lombok.extern.slf4j.Slf4j;

/**
 * PuppyWithDeadLock - class example for demonstrating DeadLock problem {@Link }
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class PuppyWithDeadLock implements Puppy {
    private final String name;

    public PuppyWithDeadLock(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public synchronized void playWithPuppy(Puppy puppyWithDeadLockFriend) {
        log.trace("{} puppy occupies {} puppy.", this.name, puppyWithDeadLockFriend.getName());
        puppyWithDeadLockFriend.barkWithPuppy(this);
        log.trace("{} release {} puppy.", this.name, puppyWithDeadLockFriend.getName());
    }

    @Override
    public synchronized void barkWithPuppy(Puppy puppyWithDeadLockFriend) {
        log.trace("{} puppy is barking with puppy - {}", this.name, puppyWithDeadLockFriend.getName());
    }
}
