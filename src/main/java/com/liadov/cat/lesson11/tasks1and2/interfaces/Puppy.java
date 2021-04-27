package com.liadov.cat.lesson11.tasks1and2.interfaces;

/**
 * Puppy - interface for sowing DeadLock condition and solution for it
 *
 * @author Aleksandr Liadov
 */
public interface Puppy {
    /**
     * Method returns name of object
     *
     * @return String value
     */
    String getName();

    /**
     * Method represents occupation of target object by current object
     * first operation
     *
     * @param puppy target object
     */
    void playWithPuppy(Puppy puppy);

    /**
     * Method represents occupation of target object by current object
     * second operation
     *
     * @param puppy target object
     */
    void barkWithPuppy(Puppy puppy);
}
