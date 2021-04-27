package com.liadov.cat.lesson11.tasks3.participant;

import com.liadov.cat.lesson11.tasks3.Chat;
import lombok.extern.slf4j.Slf4j;

/**
 * Updater - class update text of random message
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class Updater extends Participant implements Runnable {
    private final Chat chat;
    private final int minSeconds = 30;
    private final int maxSeconds = 50;

    public Updater(Chat chat) {
        this.chat = chat;
    }

    /**
     * Method updates text of random message
     */
    @Override
    public void run() {
        setCurrentThreadName();
        String className = this.getClass().getSimpleName();

        while (!Thread.currentThread().isInterrupted()) {
            sleep(minSeconds, maxSeconds);
            String message = chat.getRandom();
            String updatedMessage = message.replace("Writer", className);
            log.info("original message [{}], updated message [{}]", message, updatedMessage);
            chat.update(message, updatedMessage);
        }
    }
}
