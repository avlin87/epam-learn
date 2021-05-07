package com.liadov.cat.lesson11.tasks3.participant;

import com.liadov.cat.lesson11.tasks3.Chat;
import lombok.extern.slf4j.Slf4j;

/**
 * Writer - class write messages to Chat
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class Writer extends Participant implements Runnable {
    private final Chat chat;
    private final int minSeconds = 20;
    private final int maxSeconds = 60;

    public Writer(Chat chat) {
        this.chat = chat;
    }

    /**
     * Method sleep random amount of seconds between 20 and 60 then sends generated message to Chat
     */
    @Override
    public void run() {
        setCurrentThreadName();
        String message = "";
        boolean isMessageDelivered = true;

        while (!Thread.currentThread().isInterrupted()) {
            sleep(minSeconds, maxSeconds);
            if (isMessageDelivered) {
                message = generateMessage();
            }
            isMessageDelivered = chat.add(message);
            log.info("message [{}] sent to chat: {}", message, isMessageDelivered);
        }
    }

    private String generateMessage() {
        StringBuilder sb = new StringBuilder();
        String threadName = Thread.currentThread().getName();
        sb.append(threadName);
        sb.append(" - is the best!");
        return sb.toString();
    }
}