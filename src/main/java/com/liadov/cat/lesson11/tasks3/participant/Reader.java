package com.liadov.cat.lesson11.tasks3.participant;

import com.liadov.cat.lesson11.tasks3.Chat;
import lombok.extern.slf4j.Slf4j;

/**
 * Reader - class reads message from Chat
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class Reader extends Participant implements Runnable {
    private final Chat chat;
    private final int minSeconds = 30;
    private final int maxSeconds = 50;

    public Reader(Chat chat) {
        this.chat = chat;
    }

    /**
     * Method read message from Chat
     */
    @Override
    public void run() {
        setCurrentThreadName();

        while (!Thread.currentThread().isInterrupted()) {
            sleep(minSeconds, maxSeconds);
            String message = chat.getLastAndRemove();
            log.info("read message from chat: {}", message);
        }
    }
}
