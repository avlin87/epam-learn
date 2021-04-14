package liadov.mypackage.lesson7;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class MemoryConsumer {
    final List<String[]> arrays = new LinkedList<>();

    /**
     * Method create many objects to overuse memory
     *
     * @throws OutOfMemoryError when no available memory left
     */
    public void consume() throws OutOfMemoryError {
        log.info("consume memory process started");
        for (int i = 0; i < 10000; i++) {
            String[] strings = new String[10000];
            for (int j = 0; j < strings.length - 1; j++) {
                strings[j] = String.valueOf(System.currentTimeMillis());
            }
            arrays.add(strings);
        }
        log.info("consume memory process finished normally");
    }
}
