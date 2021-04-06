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
    private int rowCount = 0;

    /**
     * Constructor that receives full text of ADD command
     *
     * @param inputText - full text of ADD command
     */
    public AddHandler(String inputText) {
        this.inputText = inputText.split(" ");
        log.info(this.toString());
    }

    /**
     * Method validate whether it is possible to use provided command for ADD operation
     * validation message appears in case command is incorrect
     */
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

    /**
     * Method controls writing to File in case of ADD operation
     *
     * @param fileName  String - target name of a File
     * @param text      - String text to be added to File
     * @param rowNumber - int, starting point for adding text to File
     */
    private void writeTextToFile(String fileName, String text, int... rowNumber) {
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


        try (RandomAccessFile accessFile = new RandomAccessFile(targetFile, "rws")) {
            if (rowNumberProvided) {
                int startRow = rowNumber[0] - 1;
                addAdditionalRows(accessFile, startRow);
            }
            if (isFileExist) {
                existingText = getExistingTextFromFile(accessFile, rowNumberProvided, isTargetFileOriginallyEmpty);
            }
            isFormattingLineRequired = isFileExist && rowNumberProvided && (rowNumber[0] > rowCount) && (!isTargetFileOriginallyEmpty);
            addFormattingLine(accessFile, isFormattingLineRequired);

            accessFile.writeBytes(text);
            log.info("new text added successfully");

            IsDataAvailableForRestoration = rowNumberProvided && isFileExist && (existingText.size() > 0);
            restoreOldText(accessFile, IsDataAvailableForRestoration, existingText);

        } catch (FileNotFoundException e) {
            log.warn(ExceptionHandler.getStackTrace(e));
        } catch (IOException e) {
            log.warn(ExceptionHandler.getStackTrace(e));
        }
    }

    /**
     * Method restore existing text to File if required condition met
     *
     * @param accessFile                    RandomAccessFile
     * @param isDataAvailableForRestoration boolean
     * @param existingText                  List<String> text to be added to file
     * @throws IOException
     */
    private void restoreOldText(RandomAccessFile accessFile, boolean isDataAvailableForRestoration, List<String> existingText) throws IOException {
        if (isDataAvailableForRestoration) {
            for (String existingString : existingText) {
                accessFile.writeBytes("\n");
                accessFile.writeBytes(existingString);
                log.info("old text restoration process: \"{}\"", existingString);
            }
            log.info("Old text restored successfully");
        } else {
            log.info("restoration was not executed");
        }
    }

    /**
     * Method adding new row to file if required condition met
     *
     * @param accessFile               RandomAccessFile
     * @param isFormattingLineRequired boolean
     * @throws IOException
     */
    private void addFormattingLine(RandomAccessFile accessFile, boolean isFormattingLineRequired) throws IOException {
        if (isFormattingLineRequired) {
            accessFile.writeBytes("\n");
            rowCount++;
            log.info("Formatting row added. rowCount={}", rowCount);
        } else {
            log.info("Formatting row NOT added. rowCount={}", rowCount);
        }
    }

    /**
     * Method read existing text form file and return List<String> as a result
     *
     * @param accessFile                  RandomAccessFile
     * @param rowNumberProvided           boolean
     * @param isTargetFileOriginallyEmpty boolean
     * @return List<String> existing text
     * @throws IOException
     */
    private List<String> getExistingTextFromFile(RandomAccessFile accessFile, boolean rowNumberProvided, boolean isTargetFileOriginallyEmpty) throws IOException {
        String tempString = "";
        long filePointer = accessFile.getFilePointer();
        log.info("file pointer = {}", filePointer);
        List<String> existingText = new ArrayList<>();

        while (tempString != null) {
            tempString = accessFile.readLine();
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
            log.info("file Pointer changed from {} to {}", accessFile.getFilePointer(), filePointer);
            accessFile.seek(filePointer);
        } else if (!isTargetFileOriginallyEmpty) {
            log.info("new line added");
            accessFile.writeBytes("\n");
            rowCount++;
            log.info("row count = {}", rowCount);
        }
        return existingText;
    }

    /**
     * Method read target file via accessFile and add new rows to file in case new text should be added beyond existing rows.
     *
     * @param accessFile RandomAccessFile
     * @param startRow   int
     * @throws IOException
     */
    private void addAdditionalRows(RandomAccessFile accessFile, int startRow) throws IOException {
        for (int i = 0; i < startRow; i++) {
            if (accessFile.readLine() == null) {
                accessFile.writeBytes("\n");
                log.info("new row added to reach row number={}, i={}", startRow, i);
            }
            rowCount++;

            log.info("active file pointer = {}", accessFile.getFilePointer());
            log.info("row count = {}", rowCount);
        }
        log.info("blank rows adding operation finished");
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