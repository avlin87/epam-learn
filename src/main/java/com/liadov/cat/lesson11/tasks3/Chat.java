package com.liadov.cat.lesson11.tasks3;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Chat - class stores chat messages and handle possible operations
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class Chat {
    private final List<String> messages = new LinkedList<>();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final int chatCapacity = 25;
    private int count;

    /**
     * Method adds received message to List of messages
     *
     * @param smsText String target message
     * @return boolean: true - in case message added, false - in case  message was not added.
     */
    public boolean add(String smsText) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        log.trace("write locked");
        try {
            int currentSize = size();
            if (currentSize >= chatCapacity) {
                log.info("current size [{}] reached chat capacity [{}]", currentSize, chatCapacity);
                return false;
            }
            smsText = ++count + " - " + smsText;
            messages.add(smsText);
            log.debug("smsText added: [{}]", smsText);
            return true;
        } finally {
            writeLock.unlock();
            log.trace("finally: write unlocked");
        }
    }

    /**
     * Method reads last message from List of messages and removes if any available
     *
     * @return String value of message or blank text if List of messages is blank
     */
    public String getLastAndRemove() {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        log.trace("write locked");
        String lastSmsText = "";
        try {
            int currentSize = size();
            if (currentSize < 1) {
                log.debug("chat is blank");
                return lastSmsText;
            }
            lastSmsText = messages.get(currentSize - 1);
            messages.remove(lastSmsText);
            log.debug("smsText received [{}] and removed from messages List. Current chat state: {}", lastSmsText, messages);
            return lastSmsText;
        } finally {
            writeLock.unlock();
            log.trace("finally: write unlocked");
        }
    }

    /**
     * Method returns random message from list of messages.
     *
     * @return String value of message or blank text if List of messages is blank.
     */
    public String getRandom() {
        Lock readLock = rwLock.readLock();
        readLock.lock();
        log.trace("read locked");
        String smsText = "";
        try {
            int currentSize = messages.size();
            if (currentSize < 1) {
                return smsText;
            }
            int randomInt = ThreadLocalRandom.current().nextInt(0, currentSize);
            smsText = messages.get(randomInt);
            log.debug("smsText received: [{}]", smsText);
            return smsText;
        } finally {
            readLock.unlock();
            log.trace("finally: read unlocked");
        }
    }

    /**
     * Method update target message with updatedMessage text
     *
     * @param message        String value of target message
     * @param updatedMessage String value of updated message
     */
    public void update(String message, String updatedMessage) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        log.trace("write locked");
        try {
            int index = messages.indexOf(message);
            if (index >= 0) {
                messages.set(index, updatedMessage);
                log.debug("message updated from [{}] to [{}] in index = {}", message, updatedMessage, index);
            }
        } finally {
            writeLock.unlock();
            log.trace("finally: write unlocked");
        }
    }

    private int size() {
        Lock readLock = rwLock.readLock();
        readLock.lock();
        log.trace("read locked");
        try {
            int size = messages.size();
            log.debug("current size = {}", size);
            return size;
        } finally {
            readLock.unlock();
            log.trace("finally: read unlocked");
        }
    }
}