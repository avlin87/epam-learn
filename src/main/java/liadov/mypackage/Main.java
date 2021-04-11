package liadov.mypackage;

import liadov.mypackage.lesson5.CommandFilter;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        CommandFilter commandFilter = new CommandFilter();

        System.out.println("Please type \"help\" to see available commands");
        readConsoleCommand(commandFilter);
    }

    private static void readConsoleCommand(CommandFilter commandFilter) {
        String commandText = "";
        try (Scanner scanner = new Scanner(System.in);) {
            while (true) {
                boolean continueRun = true;
                if (scanner.hasNext()) {
                    commandText = scanner.nextLine();
                    scanner.reset();
                    log.info("Command received from console: {}", commandText);
                    continueRun = commandFilter.parseCommand(commandText);
                    log.info("Variable continueRun = {}", continueRun);
                }
                if (!continueRun){
                    log.info("Program stopped. Variable continueRun = {}",continueRun);
                    break;
                }
            }
        }
    }
}