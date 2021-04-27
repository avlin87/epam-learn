package com.liadov.cat;

import com.liadov.cat.lesson11.tasks1and2.DeadLockExample;
import com.liadov.cat.lesson11.tasks1and2.RaceConditionExample;
import com.liadov.cat.lesson11.tasks3.ChatHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        RaceConditionExample raceConditionExample = new RaceConditionExample();
        raceConditionExample.startRaceConditionMath();
        raceConditionExample.startNormalMath();

        DeadLockExample deadLockExample = new DeadLockExample();
        deadLockExample.startNormalPuppies();
        deadLockExample.startDeadLock();

        ChatHandler chatHandler = new ChatHandler();
        chatHandler.startChat();
    }

}