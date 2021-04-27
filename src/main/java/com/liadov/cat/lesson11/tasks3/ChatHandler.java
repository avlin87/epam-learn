package com.liadov.cat.lesson11.tasks3;

import com.liadov.cat.lesson11.tasks3.participant.Reader;
import com.liadov.cat.lesson11.tasks3.participant.Updater;
import com.liadov.cat.lesson11.tasks3.participant.Writer;

import java.util.ArrayList;
import java.util.List;

/**
 * ChatHandler - class for initiation of Chat and participants Threads
 *
 * @author Aleksandr Liadov
 */
public class ChatHandler {
    List<Thread> threadList = new ArrayList<>();

    /**
     * Method initiate Chat and participants Threads
     */
    public void startChat() {
        Chat chat = new Chat();
        for (int i = 0; i < 5; i++) {
            Writer writer = new Writer(chat);
            threadList.add(new Thread(writer));
        }

        for (int i = 0; i < 5; i++) {
            Reader reader = new Reader(chat);
            threadList.add(new Thread(reader));
        }

        for (int i = 0; i < 3; i++) {
            Updater updater = new Updater(chat);
            threadList.add(new Thread(updater));
        }

        threadList.forEach(Thread::start);
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadList.forEach(Thread::interrupt);
    }
}
