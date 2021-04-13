package liadov.mypackage;

import liadov.mypackage.lesson7.CustomClassLoader;
import liadov.mypackage.lesson7.MemoryConsumer;
import liadov.mypackage.lesson7.StackOverflowErrorProvider;
import liadov.mypackage.lesson7.exception.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        CustomClassLoader classLoader = new CustomClassLoader();
        Class clazz = classLoader.loadClass("TestClass");
        Object obj = clazz.newInstance();
        System.out.println(obj);

        callForTheFinale();


    }

    private static void callForTheFinale() {
        long start = System.currentTimeMillis();
        try {
            StackOverflowErrorProvider provider = new StackOverflowErrorProvider();
            provider.recursiveCall(1);
        } catch (StackOverflowError e) {
            log.info("catch: recursiveCall process Finished in {} seconds", ((System.currentTimeMillis() - start) / 1000.0));
            log.error(ExceptionHandler.getFullStackTrace(e,5));
            //throw e;
        } finally {
            log.info("finally: recursiveCall process Finished in {} seconds", ((System.currentTimeMillis() - start) / 1000.0));
        }

        try {
            MemoryConsumer consumer = new MemoryConsumer();
            consumer.consume();
        } catch (OutOfMemoryError e) {
            log.info("catch: consume memory process Finished in {} seconds", ((System.currentTimeMillis() - start) / 1000.0));
            log.error(ExceptionHandler.getFullStackTrace(e));
            throw e;
        } finally {
            log.info("finally: consume memory process Finished in {} seconds", ((System.currentTimeMillis() - start) / 1000.0));
        }
    }
}
