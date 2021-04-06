package liadov.mypackage.lesson5;

import liadov.mypackage.lesson5.exceptions.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

@Slf4j
public class FileAccessController {

    public void writeToFile(File targetFile) {
        try (RandomAccessFile accessFile = new RandomAccessFile(targetFile, "rws")) {

        } catch (
                FileNotFoundException e) {
            log.warn(ExceptionHandler.getStackTrace(e));
        } catch (
                IOException e) {
            log.warn(ExceptionHandler.getStackTrace(e));
        }
    }
}
