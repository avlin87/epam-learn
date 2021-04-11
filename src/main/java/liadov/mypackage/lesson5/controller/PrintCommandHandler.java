package liadov.mypackage.lesson5.controller;

import liadov.mypackage.lesson5.view.ConsolePrinter;
import liadov.mypackage.lesson5.mode.FileAccessController;
import liadov.mypackage.lesson5.exceptions.ExceptionHandler;
import liadov.mypackage.lesson5.exceptions.UnreachableRequestedRow;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
public class PrintCommandHandler extends CommandHandler {
    private ConsolePrinter consolePrinter = ConsolePrinter.getInstance();

    public void proceedPrintScenario(String commandText) throws UnreachableRequestedRow {
        String inputText[] = commandText.split(" ");
        try {
            if (validateCommand(inputText)) {
                if (getRowNumber() > 0) {
                    printTextFromFile(getFileName(), getRowNumber());
                } else {
                    printTextFromFile(getFileName());
                }
            }
        } catch (FileNotFoundException e) {
            consolePrinter.printFileNotFound();
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
            validateRequestedRowIsPresent(rowNumberProvided, existingText.size(), rowNumberProvided ? rowNumber[0] : 0);
            if (rowNumberProvided) {
                consolePrinter.printText(existingText.get(rowNumber[0] - 1));

            } else {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < existingText.size(); i++) {
                    stringBuilder.append(existingText.get(i));
                    if (i != existingText.size() - 1) {
                        stringBuilder.append("\n");
                    }
                }
                consolePrinter.printText(stringBuilder.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
