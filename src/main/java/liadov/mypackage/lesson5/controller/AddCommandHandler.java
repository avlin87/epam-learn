package liadov.mypackage.lesson5.controller;

import liadov.mypackage.lesson5.view.ConsolePrinter;
import liadov.mypackage.lesson5.mode.FileAccessController;
import liadov.mypackage.lesson5.exceptions.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class AddCommandHandler extends CommandHandler {
    ConsolePrinter consolePrinter = ConsolePrinter.getInstance();

    private String text;

    /**
     * Method validate whether it is possible to use provided command for ADD operation
     * validation message appears in case command is incorrect
     *
     * @param commandText
     * @return
     */
    @Override
    public boolean handle(String commandText) {
        String[] inputText = commandText.split(" ");
        if (validateAddCommand(inputText)) {
            parseText(inputText, getFileNamePosition());
            if (getRowNumber() > 0) {
                addTextToFile(getFileName(), text, getRowNumber());
            } else {
                addTextToFile(getFileName(), text);
            }
        }
        return false;
    }

    /**
     * Method validate provided add command
     *
     * @param inputText String validate provided add command
     * @return boolean
     */
    public boolean validateAddCommand(String[] inputText) {
        boolean rowNumberProvided;
        boolean isEnoughValuesProvided;
        try {
            setRowNumber(this.validateRowNumber(inputText[1]));
            rowNumberProvided = getRowNumber() > 0;
            log.info("row number specified in command: {}", rowNumberProvided);
            isEnoughValuesProvided = (rowNumberProvided && (getRowNumber() >= 1) && (inputText.length > 3)) || (!rowNumberProvided && (inputText.length > 2));
            log.info("EnoughValuesProvided: {}", isEnoughValuesProvided);
            if (isEnoughValuesProvided) {
                setFileNamePosition(1);
                if (rowNumberProvided) {
                    setFileNamePosition(2);
                }
                setFileName(this.parseFileName(inputText[getFileNamePosition()]));
                return true;
            } else {
                String rowNumberValidation = "";
                if (rowNumberProvided && !(getRowNumber() >= 1)) {
                    consolePrinter.printRowNumberValidation();
                    rowNumberValidation = " row number=" + getRowNumber() + ", expected value >=1";
                }
                consolePrinter.printProvideFullCommand();
                log.info("Provided ADD command is not valid: length={}, expected length>={};{}", inputText.length, rowNumberProvided ? (4) : (3), rowNumberValidation);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            consolePrinter.printProvideFullCommand();
            log.info("add command has empty details [{}], \n{}", Arrays.toString(inputText), ExceptionHandler.getStackTrace(e));
        } catch (FileNotFoundException e) {
            consolePrinter.printFileNotFound();
            log.info("File name is not valid, \n{}", ExceptionHandler.getStackTrace(e));
        }
        return false;
    }

    /**
     * Method controls writing to File in case of ADD operation
     *
     * @param fileName  String - target name of a File
     * @param text      - String text to be added to File
     * @param rowNumber - int, starting point for adding text to File
     */
    private void addTextToFile(String fileName, String text, int... rowNumber) {
        File targetFile = new File(fileName);
        boolean isFileExist = targetFile.exists();
        boolean isTargetFileOriginallyEmpty = (targetFile.length() == 0);
        boolean rowNumberProvided = rowNumber.length > 0;
        boolean isFormattingLineRequired;
        boolean IsDataAvailableForRestoration;
        List<String> existingText = null;

        log.info("target file found: {}", isFileExist);
        log.info("target file originally empty: {}", isTargetFileOriginallyEmpty);
        log.info("row number provided: {}", rowNumberProvided);

        try {
            FileAccessController fileController = new FileAccessController(targetFile);
            if (rowNumberProvided) {
                int startRow = rowNumber[0] - 1;
                fileController.skipRows(startRow, true);
            }
            if (isFileExist) {
                existingText = fileController.getExistingTextFromFile(rowNumberProvided, isTargetFileOriginallyEmpty);
            }
            isFormattingLineRequired = isFileExist && rowNumberProvided && (rowNumber[0] > fileController.getRowCount()) && (!isTargetFileOriginallyEmpty);
            addFormattingLine(fileController, isFormattingLineRequired);
            fileController.writeBytes(text);
            log.info("new text added successfully");

            IsDataAvailableForRestoration = rowNumberProvided && isFileExist && (existingText.size() > 0);
            fileController.restoreOldText(IsDataAvailableForRestoration, existingText, 0);
            consolePrinter.printRowAddedSuccessfully();
        } catch (FileNotFoundException e) {
            log.warn("requested file was not found\n{}", ExceptionHandler.getStackTrace(e));
        } catch (IOException e) {
            log.warn(ExceptionHandler.getStackTrace(e));
        }
    }

    /**
     * Method adding new row to file if required condition met
     *
     * @param fileController           FileAccessController
     * @param isFormattingLineRequired boolean
     * @throws IOException exception for file operations
     */
    private void addFormattingLine(FileAccessController fileController, boolean isFormattingLineRequired) throws IOException {
        if (isFormattingLineRequired) {
            fileController.writeBytes("\n");
            fileController.incrementRowCount();
            log.info("Formatting row added. rowCount={}", fileController.getRowCount());
        } else {
            log.info("Formatting row NOT added. rowCount={}", fileController.getRowCount());
        }
    }

    /**
     * Method pars text to be added to file from ADD command
     *
     * @param inputText        String Array of ADD command words elements
     * @param fileNamePosition start text position is based on whether row number is received or not
     */
    private void parseText(String[] inputText, int fileNamePosition) {
        StringBuilder sb = new StringBuilder();
        for (int i = fileNamePosition + 1; i < inputText.length; i++) {
            sb.append(inputText[i]);
            sb.append(" ");
        }
        if (sb.substring(0, 1).equals("\"")) {
            sb.replace(0, 1, "");
        }
        if (sb.substring(sb.length() - 2, sb.length() - 1).equals("\"")) {
            sb.replace(sb.length() - 2, sb.length() - 1, "");
        }
        text = sb.toString();
        text = text.trim();
        log.info("text parsed as: \"{}\"", text);
    }

    @Override
    public String toString() {
        return "AddHandler{" +
                "rowNumber=" + getRowNumber() +
                ", fileName='" + getFileName() + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}