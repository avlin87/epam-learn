package liadov.mypackage.lesson5;

import liadov.mypackage.lesson5.exceptions.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class AddHandler {
    private int rowNumber;
    private String fileName;
    private String text;
    private String[] inputText;

    /**
     * Constructor that receives full text of ADD command
     *
     * @param inputText - full text of ADD command
     */
    public AddHandler(String inputText) {
        this.inputText = inputText.split(" ");
        log.info(this.toString());
    }

    public void proceedAddScenario() {
        boolean rowNumberProvided;
        boolean isEnoughValuesProvided;
        try {
            rowNumberProvided = validateRowNumber(inputText[1]);
            log.info("row number specified in command: {}", rowNumberProvided);
            isEnoughValuesProvided = (rowNumberProvided && (rowNumber >= 1) && (inputText.length > 3)) || (!rowNumberProvided && (inputText.length > 2));
            log.info("EnoughValuesProvided: {}", isEnoughValuesProvided);
            if (isEnoughValuesProvided) {
                int fileNamePosition = 1;
                if (rowNumberProvided) {
                    fileNamePosition = 2;
                }
                parseFileName(inputText[fileNamePosition]);
                parseText(inputText, fileNamePosition);
                if (rowNumberProvided) {
                    writeTextToFile(fileName, text, rowNumber);
                } else {
                    writeTextToFile(fileName, text);
                }
            } else {
                String rowNumberValidation = "";
                if (rowNumberProvided && !(rowNumber >= 1)) {
                    System.out.println("row number should be =>1 (in case it is specified)");
                    rowNumberValidation = " row number=" + rowNumber + ", expected value >=1";
                }
                System.out.println("Please provide full command information. Type \"help\" to see command example");
                log.info("Provided ADD command is not valid: length={}, expected length>={};{}", inputText.length, rowNumberProvided ? 4 : 3, rowNumberValidation);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please provide full command information. Type \"help\" to see command example");
            log.info("add command has empty details [{}], \n{}", Arrays.toString(inputText), ExceptionHandler.getStackTrace(e));
        } catch (FileNotFoundException e) {
            System.out.println("File name is not valid");
            log.info("File name is not valid, \n{}", ExceptionHandler.getStackTrace(e));
        }
    }

    private void writeTextToFile(String fileName, String text, int... rowNumber) {
        String tempString = "";
        File targetFile = new File(fileName);
        boolean isFileExist = targetFile.exists();
        boolean isTargetFileOriginallyEmpty = (targetFile.length() == 0);
        log.info("target file found: {}", isFileExist);
        boolean rowNumberProvided = rowNumber.length > 0;
        log.info("row number provided: {}", rowNumberProvided);
        long filePointer;
        int rowCount = 0;
        List<String> existingText = new ArrayList<>();



        try (RandomAccessFile accessFile = new RandomAccessFile(targetFile, "rws")) {

            log.info("active file pointer = {}", accessFile.getFilePointer());

            if (rowNumberProvided) {
                int startRow = rowNumber[0] - 1;
                for (int i = 0; i < startRow; i++) {
                    if (accessFile.readLine() == null) {
                        accessFile.writeBytes("\n");
                        log.info("new row added to reach row number={}, i={}", startRow, i);
                    }
                    rowCount++;
                    log.info("active file pointer = {}", accessFile.getFilePointer());
                    log.info("row count = {}", rowCount);
                }
                log.info("blank rows added if any were needed");
            }

            if (isFileExist) {
                filePointer = accessFile.getFilePointer();
                log.info("file pointer = {}", filePointer);
                while (tempString != null) {
                    tempString = accessFile.readLine();
                    log.info("tempString = \"{}\"", tempString);
                    if (tempString == null) {
                        log.info("no existing rows found");
                        break;
                    }
                    rowCount++;
                    log.info("row count = {}", rowCount);
                    if (rowNumberProvided) {
                        existingText.add(tempString);
                        log.info("row received from file: {}", existingText.get(existingText.size() - 1));
                    } else {
                        log.info("row skipped");
                    }
                }
                log.info("active file pointer = {}", accessFile.getFilePointer());
                log.info("row count = {}", rowCount);
                log.info("rowNumberProvided = {}", rowNumberProvided);
                if (rowNumberProvided) {
                    log.info("file Pointer changed from {} to {}", accessFile.getFilePointer(), filePointer);
                    accessFile.seek(filePointer);
                } else if (!isTargetFileOriginallyEmpty) {
                    log.info("new line added");
                    accessFile.writeBytes("\n");
                    rowCount++;
                    log.info("row count = {}", rowCount);
                }
            }
            System.out.println(isFileExist);
            if ((isFileExist) && rowNumberProvided && (rowNumber[0] > rowCount) && (!isTargetFileOriginallyEmpty)) {
                accessFile.writeBytes("\n");
                rowCount++;
                log.info("row count = {}", rowCount);
                log.info("new row created: rowNumberProvided={}, rowNumber[0]={}, rowCount={}", rowNumberProvided, rowNumber.length > 0 ? rowNumber[0] : "null", rowCount);
            } else {
                log.info("new row NOT added: rowNumberProvided={}, rowNumber[0]={}, rowCount={}", rowNumberProvided, rowNumber.length > 0 ? rowNumber[0] : "null", rowCount);
            }

            accessFile.writeBytes(text);
            log.info("new text added successfully");

            if (rowNumberProvided && isFileExist && (existingText.size() > 0)) {
                for (String existingString : existingText) {
                    accessFile.writeBytes("\n");
                    accessFile.writeBytes(existingString);
                    log.info("old text restoration process: \"{}\"", existingString);
                }
            }
            log.info("write operation successful");
        } catch (FileNotFoundException e) {
            log.warn(ExceptionHandler.getStackTrace(e));
        } catch (IOException e) {
            log.warn(ExceptionHandler.getStackTrace(e));
        }
    }

    /**
     * Method returns true in case rowNumber was parsed successfully
     *
     * @param rowNumberString string from witch rowNumber parsing should be executed
     * @return true/false based on rowNumber parsing result
     */
    private boolean validateRowNumber(String rowNumberString) {
        try {
            rowNumber = Integer.parseInt(rowNumberString);
            log.info("rowNumber parsed as {}", rowNumber);
            return true;
        } catch (NumberFormatException e) {
            log.info("non Integer value provided as second word in command add [{}]", rowNumberString);
        }
        return false;
    }

    /**
     * Method pars name of file as target for ADD operation
     *
     * @param fileName String text value
     */
    private void parseFileName(String fileName) throws FileNotFoundException {
        if (fileName.length() > 0) {
            this.fileName = fileName;
            log.info("fileName parsed as {}", this.fileName);
        } else {
            throw new FileNotFoundException();
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
                "rowNumber=" + rowNumber +
                ", fileName='" + fileName + '\'' +
                ", text='" + text + '\'' +
                ", inputText=" + Arrays.toString(inputText) +
                '}';
    }
}