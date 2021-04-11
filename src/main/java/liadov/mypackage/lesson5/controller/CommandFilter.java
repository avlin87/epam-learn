package liadov.mypackage.lesson5.controller;

import liadov.mypackage.lesson5.exceptions.ExceptionHandler;
import liadov.mypackage.lesson5.exceptions.UnreachableRequestedRow;
import liadov.mypackage.lesson5.view.ConsolePrinter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommandFilter {
    private final ConsolePrinter consolePrinter = ConsolePrinter.getInstance();
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
        Map<Commands, Handler> handlerMap = new HashMap<>();
        handlerMap.put(Commands.ADD, new AddCommandHandler());
        handlerMap.put(Commands.DELETE, new DeleteCommandHandler());
        handlerMap.put(Commands.PRINT, new PrintCommandHandler());
        handlerMap.put(Commands.HELP, new CommandHandler());
        handlerMap.put(Commands.EXIT, new CommandHandler());

        try {
            log.info("Command handle requested: {}, Command text {}",command, commandText);
            return handlerMap.get(command).handle(commandText);
        } catch (UnreachableRequestedRow e) {
            consolePrinter.printRowNumberNotReached();
            log.warn("Requested row can not be reached\n{}", ExceptionHandler.getStackTrace(e));
        }
        return true;
    }
}
