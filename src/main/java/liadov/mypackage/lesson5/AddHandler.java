package liadov.mypackage.lesson5;

import liadov.mypackage.lesson5.exceptions.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class AddHandler extends CommandHandler {

    private String text;

    /**
     * Constructor that receives full text of ADD command
     *
     * @param inputText - full text of ADD command
     */
    public AddHandler(String inputText) {
        setInputText(inputText.split(" "));
        log.info(this.toString());
    }

    /**
     * Method validate whether it is possible to use provided command for ADD operation
     * validation message appears in case command is incorrect
     */
    public void proceedAddScenario() {
        if (validateCommand(2, 3)) {
            parseText(getInputText(), getFileNamePosition());
            if (getRowNumber() > 0) {
                addTextToFile(getFileName(), text, getRowNumber());
            } else {
                addTextToFile(getFileName(), text);
            }
        }
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

        try (FileAccessController fileController = new FileAccessController(targetFile)) {
            if (rowNumberProvided) {
                int startRow = rowNumber[0] - 1;
                fileController.skipRows(startRow, true);
            }
            if (isFileExist) {
                existingText = fileController.getExistingTextFromFile(rowNumberProvided, isTargetFileOriginallyEmpty);
            }
            isFormattingLineRequired = isFileExist && rowNumberProvided && (rowNumber[0] > fileController.getRowCount()) && (!isTargetFileOriginallyEmpty);
            addFormattingLine(fileController, isFormattingLineRequired);
            System.out.println(text);
            fileController.writeBytes(text);
            log.info("new text added successfully");

            IsDataAvailableForRestoration = rowNumberProvided && isFileExist && (existingText.size() > 0);
            fileController.restoreOldText(IsDataAvailableForRestoration, existingText, 0);

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
                ", inputText=" + Arrays.toString(getInputText()) +
                '}';
    }
}