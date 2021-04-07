package liadov.mypackage.lesson5;

import liadov.mypackage.lesson5.exceptions.ExceptionHandler;
import liadov.mypackage.lesson5.exceptions.UnreachableRequestedRow;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
public class PrintHandler extends CommandHandler {
    private ConsolePrinter consolePrinter = ConsolePrinter.getInstance();

    public PrintHandler(String commandText) {
        setInputText(commandText.split(" "));
        log.info("print command initiated");
    }

    public void proceedPrintScenario() throws UnreachableRequestedRow {
        try {
            if (validateCommand(1, 2)) {
                if (getRowNumber() > 0) {
                    printTextFromFile(getFileName(), getRowNumber());
                } else {
                    printTextFromFile(getFileName());
                }
            }
        } catch (FileNotFoundException e) {
            log.warn("Source file was not found\n{}", ExceptionHandler.getStackTrace(e));
        }
    }

    private void printTextFromFile(String fileName, int... rowNumber) throws FileNotFoundException {
        File targetFile = new File(fileName);
        validateFile(targetFile);
        boolean rowNumberProvided = rowNumber.length > 0;
        boolean isTargetFileOriginallyEmpty = (targetFile.length() == 0);
        List<String> existingText;
        log.info("row number provided: {}", rowNumberProvided);

        try (FileAccessController fileController = new FileAccessController(targetFile)) {
            existingText = fileController.getExistingTextFromFile(true, true);
            validateRequestedRowIsPresent(rowNumberProvided, existingText.size(), rowNumber[0]);
            if (rowNumberProvided) {
                consolePrinter.printText(existingText.get(rowNumber[0] - 1));

            } else {
                for (String s : existingText) {
                    consolePrinter.printText(s);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
