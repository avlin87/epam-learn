package liadov.mypackage.lesson5;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileAccessController extends RandomAccessFile {
    private int rowCount;
    private long filePointerBeforeLastRow;

    public FileAccessController(File file) throws FileNotFoundException {
        super(file, "rws");
        rowCount = 0;
    }

    /**
     * Method read target file via accessFile and add new rows to file in case new text should be added beyond existing rows.
     *
     * @param startRow int
     * @throws IOException exception for file operations
     */
    public void skipRows(int startRow, boolean canAddRows) throws IOException {
        for (int i = 0; i < startRow; i++) {
            if ((this.readLine() == null) && canAddRows) {
                this.writeBytes("\n");
                log.info("new row added to reach row number={}, i={}", startRow, i);
            }
            this.incrementRowCount();

            log.info("active file pointer = {}", this.getFilePointer());
            log.info("row count = {}", this.getRowCount());
        }
        log.info("blank rows adding operation finished");
    }

    /**
     * Method read existing text form file and return List<String> as a result
     *
     * @param rowNumberProvided           boolean
     * @param isTargetFileOriginallyEmpty boolean
     * @return List<String> existing text
     * @throws IOException exception for file operations
     */
    public List<String> getExistingTextFromFile(boolean rowNumberProvided, boolean isTargetFileOriginallyEmpty) throws IOException {

        long filePointer = this.getFilePointer();
        log.info("file pointer = {}", filePointer);
        List<String> existingText = new ArrayList<>();

        while (true) {
            filePointerBeforeLastRow = getFilePointer();
            String tempString = this.readLine();
            log.info("row received from FILE: \"{}\"", tempString);
            if (tempString == null) {
                log.info("no existing rows found");
                break;
            }
            if (rowNumberProvided) {
                existingText.add(tempString);
                log.info("row added to collection: {}", existingText.get(existingText.size() - 1));
            } else {
                log.info("row skipped");
            }
            incrementRowCount();
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
     * @throws IOException exception for file operations
     */
    public void restoreOldText(boolean isDataAvailableForRestoration, List<String> existingText, int restorationStartPoint) throws IOException {
        log.info("is Data available for restoration={}, restoration point={}", isDataAvailableForRestoration, restorationStartPoint);
        if (isDataAvailableForRestoration) {
            for (int i = restorationStartPoint; i < existingText.size(); i++) {
                if (getFilePointer() > 0) {
                    this.writeBytes("\n");
                }
                this.writeBytes(existingText.get(i));
                log.info("old text restoration process: \"{}\"", existingText.get(i));
            }
            log.info("Old text restored successfully");
        } else {
            log.info("restoration was not executed");
        }
    }

    /**
     * Method return value of file pointer to last existing row witch is set during execution of getExistingTextFromFile()
     *
     * @return long file point to last row
     */
    public long getFilePointerBeforeLastRow() {
        return filePointerBeforeLastRow;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void incrementRowCount() {
        rowCount++;
    }
}
