package liadov.mypackage.lesson5.controller;

import liadov.mypackage.lesson5.view.ConsolePrinter;
import liadov.mypackage.lesson5.exceptions.ExceptionHandler;
import liadov.mypackage.lesson5.exceptions.UnreachableRequestedRow;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

@Slf4j
public class CommandHandler implements Handler {
    private int rowNumber;
    private String fileName;
    private int fileNamePosition;
    private final ConsolePrinter consolePrinter = ConsolePrinter.getInstance();

    /**
     * Method execute common operation for "help" and "exit" operations
     *
     * @param commandText String text of received command
     * @return boolean false in case of 'exit' command
     */
    @Override
    public boolean handle(String commandText) {
        log.info("received: {}", commandText);
        if (commandText.equalsIgnoreCase("HELP")) {
            log.info("HELP command executed");
            consolePrinter.printAllAvailableCommands();
        } else if (commandText.equalsIgnoreCase("EXIT")) {
            log.info("EXIT command executed");
            consolePrinter.printFinishProgram();
            return false;
        }
        log.info("handle() finished");
        return true;
    }

    /**
     * Method validate correctness of received command
     *
     * @param inputText String[] command text
     * @return true in case validation passed, false in case validation met problems
     */
    protected boolean validateCommand(String[] inputText) {
        boolean rowNumberProvided;
        boolean isEnoughValuesProvided;
        try {
            rowNumberProvided = setRowNumberIfValid(inputText[1]);
            log.info("row number specified in command: {}", rowNumberProvided);
            isEnoughValuesProvided = !rowNumberProvided || getRowNumber() >= 1 && inputText.length > 2;
            log.info("EnoughValuesProvided: {}", isEnoughValuesProvided);
            if (isEnoughValuesProvided) {
                setFileNamePosition(1);
                if (rowNumberProvided) {
                    setFileNamePosition(2);
                }
                setFileName(this.parseFileName(inputText[getFileNamePosition()]));
                validateFile(new File(getFileName()));
                return true;
            } else {
                String rowNumberValidation = "";
                if ((getRowNumber() < 1)) {
                    consolePrinter.printRowNumberValidation();
                    rowNumberValidation = " row number=" + getRowNumber() + ", expected value >=1";
                }
                consolePrinter.printProvideFullCommand();
                log.info("Provided command is not valid: length={}, expected length>={};{}", inputText.length, 3, rowNumberValidation);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            consolePrinter.printProvideFullCommand();
            log.info("command has empty details {}, \n{}", Arrays.toString(inputText), ExceptionHandler.getStackTrace(e));
        } catch (FileNotFoundException e) {
            consolePrinter.printFileNotFound();
            log.info("File name is not valid, \n{}", ExceptionHandler.getStackTrace(e));
        }
        return false;
    }

    /**
     * Method returns true in case rowNumber was parsed successfully
     *
     * @param rowNumberString string from witch rowNumber parsing should be executed
     * @return true/false based on rowNumber parsing result
     */
    protected boolean setRowNumberIfValid(String rowNumberString) {
        int rowNumber;
        try {
            rowNumber = Integer.parseInt(rowNumberString);
            setRowNumber(rowNumber);
            log.info("rowNumber parsed as {}", rowNumber);
            return true;
        } catch (NumberFormatException e) {
            log.info("non Integer value provided as second word in command add [{}]", rowNumberString);
            return false;
        }
    }

    /**
     * Method pars name of file as target for ADD operation
     *
     * @param fileName String text value
     */
    protected String parseFileName(String fileName) throws FileNotFoundException {
        if (fileName.length() > 0) {
            log.info("fileName parsed as {}", fileName);
            return fileName;
        } else {
            consolePrinter.printFileNotFound();
            log.info("target file was not found: {}", fileName);
            throw new FileNotFoundException();
        }
    }

    /**
     * Method Validate file if presented
     *
     * @param targetFile File to be checked
     * @throws FileNotFoundException in case expected file is absent
     */
    protected void validateFile(File targetFile) throws FileNotFoundException {
        if (!targetFile.exists()) {
            consolePrinter.printFileNotFound();
            log.info("target file was not found: {}", fileName);
            throw new FileNotFoundException();
        }
    }

    /**
     * Method validates if requested row is present in file.
     *
     * @param rowNumberProvided boolean row number is present in command
     * @param size              int number of rows present in target file
     * @param rowNumber         int number of row to be found
     * @throws UnreachableRequestedRow thrown in case requested row is out of file range
     */
    protected void validateRequestedRowIsPresent(boolean rowNumberProvided, int size, int rowNumber) throws UnreachableRequestedRow {
        if (rowNumberProvided && (size < rowNumber)) {
            log.warn("requested row is not present in existing text. Existing text size {}, requested row {}", size, rowNumber);
            throw new UnreachableRequestedRow();
        }
        log.info("requested row number present in file");
    }

    protected int getRowNumber() {
        return rowNumber;
    }

    protected void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    protected String getFileName() {
        return fileName;
    }

    protected void setFileName(String fileName) {
        this.fileName = fileName;
    }

    protected int getFileNamePosition() {
        return fileNamePosition;
    }

    protected void setFileNamePosition(int fileNamePosition) {
        this.fileNamePosition = fileNamePosition;
    }

}
