package liadov.mypackage.lesson5;

import liadov.mypackage.lesson5.exceptions.ExceptionHandler;
import liadov.mypackage.lesson5.exceptions.UnreachableRequestedRow;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

@Slf4j
public class CommandHandler {
    private int rowNumber;
    private String fileName;
    private int fileNamePosition;
    private String[] inputText;
    private ConsolePrinter consolePrinter = ConsolePrinter.getInstance();

    /**
     * Method validate correctness of received command
     *
     * @param minWithoutNumber minimum number of validates in case number was not populated
     * @param minWithNumber    minimum number of validates in case number was populated
     * @return true in case validation passed, false in case validation met problems
     */
    public boolean validateCommand(int minWithoutNumber, int minWithNumber) {
        boolean rowNumberProvided;
        boolean isEnoughValuesProvided;
        try {
            setRowNumber(this.validateRowNumber(getInputText()[1]));
            rowNumberProvided = getRowNumber() > 0;
            log.info("row number specified in command: {}", rowNumberProvided);
            isEnoughValuesProvided = (rowNumberProvided && (getRowNumber() >= 1) && (getInputText().length > minWithNumber)) || (!rowNumberProvided && (getInputText().length > minWithoutNumber));
            log.info("EnoughValuesProvided: {}", isEnoughValuesProvided);
            if (isEnoughValuesProvided) {
                setFileNamePosition(1);
                if (rowNumberProvided) {
                    setFileNamePosition(2);
                }
                setFileName(this.parseFileName(getInputText()[getFileNamePosition()]));
                return true;
            } else {
                String rowNumberValidation = "";
                if (rowNumberProvided && !(getRowNumber() >= 1)) {
                    consolePrinter.printRowNumberValidation();
                    rowNumberValidation = " row number=" + getRowNumber() + ", expected value >=1";
                }
                consolePrinter.printProvideFullCommand();
                log.info("Provided ADD command is not valid: length={}, expected length>={};{}", getInputText().length, rowNumberProvided ? (minWithNumber + 1) : (minWithoutNumber + 1), rowNumberValidation);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            consolePrinter.printProvideFullCommand();
            log.info("add command has empty details [{}], \n{}", Arrays.toString(getInputText()), ExceptionHandler.getStackTrace(e));
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
    public int validateRowNumber(String rowNumberString) {
        int rowNumber;
        try {
            rowNumber = Integer.parseInt(rowNumberString);
            log.info("rowNumber parsed as {}", rowNumber);
            return rowNumber;
        } catch (NumberFormatException e) {
            log.info("non Integer value provided as second word in command add [{}]", rowNumberString);
        }
        return -1;
    }

    /**
     * Method pars name of file as target for ADD operation
     *
     * @param fileName String text value
     */
    public String parseFileName(String fileName) throws FileNotFoundException {
        if (fileName.length() > 0) {
            log.info("fileName parsed as {}", fileName);
            return fileName;
        } else {
            consolePrinter.printFileNotFound();
            log.info("target file was not found: {}", fileName);
            throw new FileNotFoundException();
        }
    }

    public void validateFile(File targetFile) throws FileNotFoundException {
        if (!targetFile.exists()) {
            consolePrinter.printFileNotFound();
            log.info("target file was not found: {}", fileName);
            throw new FileNotFoundException();
        }
    }

    public void validateRequestedRowIsPresent(boolean rowNumberProvided, int size, int rowNumber) throws UnreachableRequestedRow {
        if (rowNumberProvided && (size < rowNumber)) {
            consolePrinter.printRowNumberNotReached();
            log.warn("requested row is not present in existing text. Existing text size {}, requested row {}", size, rowNumber);
            throw new UnreachableRequestedRow();
        }
        log.info("requested row number present in file");
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getInputText() {
        return inputText;
    }

    public void setInputText(String[] inputText) {
        this.inputText = inputText;
    }

    public int getFileNamePosition() {
        return fileNamePosition;
    }

    public void setFileNamePosition(int fileNamePosition) {
        this.fileNamePosition = fileNamePosition;
    }
}
