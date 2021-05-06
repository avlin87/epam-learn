package com.liadov.cat;

import com.liadov.cat.lesson11.tasks1and2.DeadLockExample;
import com.liadov.cat.lesson11.tasks1and2.RaceConditionExample;
import com.liadov.cat.lesson11.tasks3.ChatHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        var raceConditionExample = new RaceConditionExample();
        raceConditionExample.startRaceConditionMath();
        raceConditionExample.startNormalMath();
        raceConditionExample.startNormalAtomicMath();

        var deadLockExample = new DeadLockExample();
        deadLockExample.startNormalPuppies();
        deadLockExample.startDeadLock();

        var chatHandler = new ChatHandler();
        chatHandler.startChat();
    }
}