package liadov.mypackage;

import liadov.mypackage.lesson7.CustomClassLoader;
import liadov.mypackage.lesson7.MemoryConsumer;
import liadov.mypackage.lesson7.StackOverflowErrorProvider;
import liadov.mypackage.lesson7.exception.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

@Slf4j
public class Main {

    public static void main(String[] args) {

        loadCompiledClass();

        callForErrors();
    }

    /**
     * Method use CustomClassLoader to create object of compiled class and print it to console
     */
    private static void loadCompiledClass() {
        try {
            CustomClassLoader classLoader = new CustomClassLoader();

            classLoader.setMyClasses("D:/java/myClasses/");

            Class clazz = classLoader.loadClass("TestClass");
            Object obj = clazz.getDeclaredConstructor().newInstance();
            System.out.println(obj);
            log.info("message received successfully as \"{}\"", obj.toString());
        } catch (IllegalAccessException e) {
            log.warn("method does not have access to the definition of the specified class\n{}", ExceptionHandler.getFullStackTrace(e));
        } catch (InstantiationException e) {
            log.warn("specified class object cannot be instantiated\n{}", ExceptionHandler.getFullStackTrace(e));
        } catch (ClassNotFoundException e) {
            log.warn("requested Class was not found\n{}", ExceptionHandler.getFullStackTrace(e, 5));
        } catch (NoSuchMethodException e) {
            log.warn("requested Method was not found\n{}", ExceptionHandler.getFullStackTrace(e, 5));
        } catch (InvocationTargetException e) {
            log.warn("requested Method or Constructor trows exception\n{}", ExceptionHandler.getFullStackTrace(e, 5));
        }
    }

    /**
     * Method use StackOverflowErrorProvider to generate StackOverflowError
     * and
     * Method use MemoryConsumer to generate OutOfMemoryError
     */
    private static void callForErrors() {
        long start = System.currentTimeMillis();
        try {
            StackOverflowErrorProvider provider = new StackOverflowErrorProvider();
            provider.recursiveCall(1);
        } catch (StackOverflowError e) {
            log.info("catch: recursiveCall process Finished in {} seconds", ((System.currentTimeMillis() - start) / 1000.0));
            log.error(ExceptionHandler.getFullStackTrace(e, 5));
        } finally {
            log.info("finally: recursiveCall process Finished in {} seconds", ((System.currentTimeMillis() - start) / 1000.0));
        }

        try {
            MemoryConsumer consumer = new MemoryConsumer();
            consumer.consume();
        } catch (OutOfMemoryError e) {
            log.info("catch: consume memory process Finished in {} seconds", ((System.currentTimeMillis() - start) / 1000.0));
            log.error(ExceptionHandler.getFullStackTrace(e));
        } finally {
            log.info("finally: consume memory process Finished in {} seconds", ((System.currentTimeMillis() - start) / 1000.0));
        }
    }
}
