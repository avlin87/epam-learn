package liadov.mypackage.lesson5;

import liadov.mypackage.lesson5.exceptions.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
public class DeleteHandler extends CommandHandler {

    public DeleteHandler(String commandText) {
        setInputText(commandText.split(" "));
    }

    public void proceedDeleteScenario() {
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

    private void deleteTextFromFile(String fileName, int... rowNumber) throws FileNotFoundException {
        File targetFile = new File(fileName);
        if (!targetFile.exists()) {
            throw new FileNotFoundException();
        }
        boolean rowNumberProvided = rowNumber.length > 0;
        boolean isTargetFileOriginallyEmpty = (targetFile.length() == 0);
        List<String> existingText = null;
        long filePointDeleteStart;
        long filePointDeleteEnd;

        try (FileAccessController fileController = new FileAccessController(targetFile)) {
            existingText = fileController.getExistingTextFromFile(rowNumberProvided, isTargetFileOriginallyEmpty);
            if (rowNumberProvided && existingText.size() > rowNumber[0]) {
                while (fileController.getRowCount()<rowNumber[0]){
                    fileController.readLine();
                    fileController.incrementRowCount();
                }
                filePointDeleteStart = fileController.getFilePointer();
                log.info("selected row for removal: \"{}\"" ,fileController.readLine());
                filePointDeleteEnd = fileController.getFilePointer();
                fileController.seek(filePointDeleteStart);
                fileController.restoreOldText(true, existingText);
                log.info("file pointer start {} , end {}",filePointDeleteStart, filePointDeleteEnd);
                fileController.setLength(fileController.getFilePointer() - (filePointDeleteEnd - filePointDeleteStart));
            }
        } catch (IOException e) {
            log.warn(ExceptionHandler.getStackTrace(e));
        }

    }
}
