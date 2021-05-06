package com.liadov.cat.lesson11.tasks3;

import com.liadov.cat.lesson11.tasks3.participant.Reader;
import com.liadov.cat.lesson11.tasks3.participant.Updater;
import com.liadov.cat.lesson11.tasks3.participant.Writer;

import java.util.concurrent.*;

/**
 * ChatHandler - class for initiation of Chat and participants Threads
 *
 * @author Aleksandr Liadov
 */
public class ChatHandler {

    /**
     * Method initiate Chat and participants Threads
     */
    public void startChat() {
        var chat = new Chat();
        ThreadFactory threadFactory = Thread::new;
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(15, threadFactory);

        for (var i = 0; i < 5; i++) {
            var writer = new Writer(chat);
            var reader = new Reader(chat);
            scheduleExecution(writer, scheduledThreadPool);
            scheduleExecution(reader, scheduledThreadPool);
        }

        for (var i = 0; i < 3; i++) {
            var updater = new Updater(chat);
            scheduleExecution(updater, scheduledThreadPool);
        }

        scheduledThreadPool.shutdown();
    }

    private void scheduleExecution(Runnable runnable, ScheduledExecutorService scheduledThreadPool) {
        int shutDownTimeOut = 15;
        Future<?> executionHandler = scheduledThreadPool.submit(runnable);
        Runnable executionStopper = () -> executionHandler.cancel(true);
        scheduledThreadPool.schedule(executionStopper, shutDownTimeOut, TimeUnit.MINUTES);
    }
}
