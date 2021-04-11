package liadov.mypackage.lesson5.view;

import liadov.mypackage.lesson5.controller.Commands;

public class ConsolePrinter {
    private static ConsolePrinter consolePrinter;

    private ConsolePrinter() {
    }

    public static ConsolePrinter getInstance() {
        if (consolePrinter == null) {
            consolePrinter = new ConsolePrinter();
        }
        return consolePrinter;
    }

    public void printFinishProgram() {
        System.out.println("\n*** Program successfully finished. Have a good day! ***");
    }

    public void printCommandUnidentified() {
        System.out.println("Command unidentified. Please type \"help\" to see available commands");
    }

    public void printRowNumberValidation() {
        System.out.println("row number should be =>1 (in case it is specified)");
    }

    public void printProvideFullCommand() {
        System.out.println("Please provide full command information. Type \"help\" to see command example");
    }

    public void printFileNotFound() {
        System.out.println("File name is not valid");
    }

    /**
     * Method prints all of available commands to console
     */
    public void printAllAvailableCommands() {
        StringBuilder sb = new StringBuilder();
        sb.append("Please see list of all available commands:");
        for (Commands command : Commands.values()) {
            sb.append("\n");
            sb.append(command.toString());
            sb.append(" - ");
            sb.append(command.getDESCRIPTION());
        }
        sb.append("\nPlease Note: number is optional for commands");
        System.out.println(sb);
    }

    public void printText(String s) {
        System.out.println("Requested content: \n\""+s+"\"");
    }

    public void printRowAddedSuccessfully() {
        System.out.println("Row added successfully");
    }

    public void printRowNumberNotReached() {
        System.out.println("Requested row can not be reached");
    }

    public void printRowRemoved() {
        System.out.println("Row removed successfully");
    }
}
