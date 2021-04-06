package liadov.mypackage.lesson5;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandFilter {

    /**
     * Method parsing first word of command
     *
     * @param commandText
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
            System.out.println("Command unidentified. Please type \"help\" to see available commands");
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
        switch (command) {
            case ADD: {
                log.info("ADD command identified");
                AddHandler addHandler = new AddHandler(commandText);
                addHandler.proceedAddScenario();
                break;
            }
            case DELETE: {
                log.info("DELETE command identified");
                DeleteHandler deleteHandler = new DeleteHandler(commandText);
                deleteHandler.proceedDeleteScenario();
                break;
            }
            case PRINT: {
                log.info("PRINT command identified");
                break;
            }
            case HELP: {
                log.info("HELP command identified");
                command.printAllAvailableCommands();
                break;
            }
            case EXIT: {
                log.info("EXIT command identified");
                System.out.println("\n*** Program successfully finished. Have a good day! ***");
                return false;
            }
        }
        return true;
    }
}
