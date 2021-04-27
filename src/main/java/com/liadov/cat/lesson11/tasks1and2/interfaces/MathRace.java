package com.liadov.cat.lesson11.tasks1and2.interfaces;

/**
 * MathRace - interface for showing example of java Race Condition and solution for it.
 *
 * @author Aleksandr Liadov
 */
public interface MathRace extends Runnable {
    /**
     * Method increment variable by one
     */
    void increment();

    /**
     * Method decrement variable by one
     */
    void decrement();

    /**
     * Method return int value of variable
     *
     * @return int value of variable
     */
    int getValue();
}
