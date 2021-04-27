package com.liadov.cat.lesson11.tasks1and2.solution;

import com.liadov.cat.lesson11.tasks1and2.interfaces.Puppy;
import lombok.extern.slf4j.Slf4j;

/**
 * PuppyNormal - class example with solved DeadLock problem
 * {@link com.liadov.cat.lesson11.tasks1and2.problem.PuppyWithDeadLock}
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class PuppyNormal implements Puppy {
    private final String name;

    public PuppyNormal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void playWithPuppy(Puppy puppy) {
        synchronized (puppy) {
            log.trace("{} puppy occupies {} puppy.", this.name, puppy.getName());
        }
        log.trace("{} release {} puppy.", this.name, puppy.getName());
        puppy.barkWithPuppy(this);
    }

    @Override
    public void barkWithPuppy(Puppy puppy) {
        synchronized (puppy) {
            log.trace("{} puppy occupies {} and barking", this.name, puppy.getName());
        }
        log.trace("{} puppy release {}", this.name, puppy.getName());
    }
}
