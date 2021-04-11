package liadov.mypackage.lesson5.controller;

import liadov.mypackage.lesson5.exceptions.ExceptionHandler;
import liadov.mypackage.lesson5.exceptions.UnreachableRequestedRow;
import liadov.mypackage.lesson5.view.ConsolePrinter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandFilter {
    private ConsolePrinter consolePrinter = ConsolePrinter.getInstance();
    /**
     * Method parsing first word of command
     *
     * @param commandText text of received command
     * @return boolean; false - Exit command received, true - not exit command
     */
    public boolean parseCommand(String commandText) {
        String firstWord = commandText.split(" ")[0];
        log.info("first Word parsed as [{}] from received line [{}]", firstWord, commandText);
        try {
            Commands command = Commands.valueOf(firstWord.toUpperCase());
            log.info("Command [{}] is present in Enum", command.toString());
            return chooseHandler(command, commandText);
        } catch (IllegalArgumentException e) {
            log.warn(e.toString());
            consolePrinter.printCommandUnidentified();
        }
        return true;
    }

    /**
     * Method choosing handler for specific command
     *
     * @param command     - Enum value
     * @param commandText - Whole text from console
     * @return boolean; false - Exit command received, true - not exit command
     */
    private boolean chooseHandler(Commands command, String commandText) {
        try {
            switch (command) {
                case ADD: {
                    log.info("ADD command identified");
                    AddCommandHandler addCommandHandler = new AddCommandHandler();
                    addCommandHandler.proceedAddScenario(commandText);
                    break;
                }
                case DELETE: {
                    log.info("DELETE command identified");
                    DeleteCommandHandler deleteCommandHandler = new DeleteCommandHandler();
                    deleteCommandHandler.proceedDeleteScenario(commandText);
                    break;
                }
                case PRINT: {
                    log.info("PRINT command identified");
                    PrintCommandHandler printCommandHandler = new PrintCommandHandler();
                    printCommandHandler.proceedPrintScenario(commandText);
                    break;
                }
                case HELP: {
                    log.info("HELP command identified");
                    consolePrinter.printAllAvailableCommands();
                    break;
                }
                case EXIT: {
                    log.info("EXIT command identified");
                    consolePrinter.printFinishProgram();
                    return false;
                }
            }
        } catch (UnreachableRequestedRow e) {
            consolePrinter.printRowNumberNotReached();
            log.warn("Requested row can not be reached\n{}", ExceptionHandler.getStackTrace(e));
        }
        return true;
    }
}
