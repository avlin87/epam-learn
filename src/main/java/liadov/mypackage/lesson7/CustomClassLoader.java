package liadov.mypackage.lesson7;

import liadov.mypackage.lesson7.exception.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class CustomClassLoader extends ClassLoader {

    private String myClasses = "D:/java/myClasses/";

    /**
     * Method is triggered by loadClass() in case ClassLoader not able to find requested class.
     * Method searches for requested class.
     *
     * @param compiledClassName String name of class without file extension
     * @return Class<?>
     */
    @Override
    public Class<?> findClass(String compiledClassName) {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(myClasses + compiledClassName + ".class"));
        } catch (FileNotFoundException e) {
            log.warn("File of requested class is absent\n{}", ExceptionHandler.getFullStackTrace(e));
        } catch (IOException e) {
            log.warn("Unexpected IOException\n{}", ExceptionHandler.getFullStackTrace(e));
        }
        return defineClass(compiledClassName, bytes, 0, bytes.length);
    }

    /**
     * Method change existing path to compiled classes
     *
     * @param myClasses String path to compiled classes
     */
    public void setMyClasses(String myClasses) {
        log.info("new directory with classes specified: {}", myClasses);
        this.myClasses = myClasses;
    }
}
