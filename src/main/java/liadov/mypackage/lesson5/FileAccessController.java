package liadov.mypackage.lesson5;

import liadov.mypackage.lesson5.exceptions.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileAccessController extends RandomAccessFile {
    private int rowCount;

    public FileAccessController(File file) throws FileNotFoundException {
        super(file, "rws");
        rowCount = 0;
    }

    /**
     * Method read existing text form file and return List<String> as a result
     *
     * @param rowNumberProvided           boolean
     * @param isTargetFileOriginallyEmpty boolean
     * @return List<String> existing text
     * @throws IOException
     */
    public List<String> getExistingTextFromFile(boolean rowNumberProvided, boolean isTargetFileOriginallyEmpty) throws IOException {
        String tempString = "";
        long filePointer = this.getFilePointer();
        log.info("file pointer = {}", filePointer);
        List<String> existingText = new ArrayList<>();

        while (tempString != null) {
            tempString = this.readLine();
            log.info("row received from FILE: \"{}\"", tempString);
            if (tempString == null) {
                log.info("no existing rows found");
                break;
            }
            if (rowNumberProvided) {
                existingText.add(tempString);
                log.info("row received from file: {}", existingText.get(existingText.size() - 1));
            } else {
                log.info("row skipped");
            }
            rowCount++;
            log.info("row count = {}", rowCount);
        }
        if (rowNumberProvided) {
            log.info("file Pointer changed from {} to {}", this.getFilePointer(), filePointer);
            this.seek(filePointer);
        } else if (!isTargetFileOriginallyEmpty) {
            log.info("new line added");
            this.writeBytes("\n");
            rowCount++;
            log.info("row count = {}", rowCount);
        }
        return existingText;
    }


    /**
     * Method restore existing text to File if required condition met
     *
     * @param isDataAvailableForRestoration boolean
     * @param existingText                  List<String> text to be added to file
     * @throws IOException
     */
    public void restoreOldText(boolean isDataAvailableForRestoration, List<String> existingText) throws IOException {
        if (isDataAvailableForRestoration) {
            for (String existingString : existingText) {
                this.writeBytes("\n");
                this.writeBytes(existingString);
                log.info("old text restoration process: \"{}\"", existingString);
            }
            log.info("Old text restored successfully");
        } else {
            log.info("restoration was not executed");
        }
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void incrementRowCount(){
        rowCount++;
    }
}
