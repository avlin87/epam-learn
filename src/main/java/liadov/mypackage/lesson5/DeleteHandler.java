package liadov.mypackage.lesson5;

import liadov.mypackage.lesson5.exceptions.ExceptionHandler;
import liadov.mypackage.lesson5.exceptions.UnreachableRequestedRow;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
public class DeleteHandler extends CommandHandler {

    public DeleteHandler(String commandText) {
        setInputText(commandText.split(" "));
        log.info("delete command initiated");
    }

    public void proceedDeleteScenario() throws UnreachableRequestedRow {
        try {
            if (validateCommand(1, 2)) {
                if (getRowNumber() > 0) {
                    deleteTextFromFile(getFileName(), getRowNumber());
                } else {
                    deleteTextFromFile(getFileName());
                }
            }
        } catch (FileNotFoundException e) {
            log.warn(ExceptionHandler.getStackTrace(e));
        }
    }

    private void deleteTextFromFile(String fileName, int... rowNumber) throws FileNotFoundException, UnreachableRequestedRow {
        File targetFile = new File(fileName);
        validateFile(targetFile);
        boolean rowNumberProvided = rowNumber.length > 0;
        boolean isTargetFileOriginallyEmpty = (targetFile.length() == 0);
        List<String> existingText;
        long filePointDeleteStart;
        long filePointDeleteEnd;

        log.info("target file originally empty: {}", isTargetFileOriginallyEmpty);
        log.info("row number provided: {}", rowNumberProvided);

        try (FileAccessController fileController = new FileAccessController(targetFile)) {
            existingText = fileController.getExistingTextFromFile(rowNumberProvided, isTargetFileOriginallyEmpty);
            log.info("row number provided {}, existing text size {}, row number {}", rowNumberProvided, existingText.size(), rowNumberProvided ? rowNumber[0] : "N/A");
            validateRequestedRowIsPresent(rowNumberProvided, existingText.size(), rowNumber[0]);

            if (rowNumberProvided) {
                fileController.skipRows(rowNumber[0] - 1, false);
                filePointDeleteStart = fileController.getFilePointer();
            } else {
                filePointDeleteStart = fileController.getFilePointerBeforeLastRow() - 1;
            }
            log.info("file Point Delete Start ={}", filePointDeleteStart);
            log.info("selected row for removal: \"{}\"", fileController.readLine());
            filePointDeleteEnd = fileController.getFilePointer();
            fileController.setLength(filePointDeleteStart > 0 ? (filePointDeleteStart - 1) : filePointDeleteStart);
            log.info("file size reduced {}", fileController.getFilePointer());
            fileController.restoreOldText(existingText.size() > 0, existingText, getRowNumber());
            log.info("file pointer start {} , end {}, current {}", filePointDeleteStart, filePointDeleteEnd, fileController.getFilePointer());

        } catch (IOException e) {
            log.warn(ExceptionHandler.getStackTrace(e));
        }
    }
}
