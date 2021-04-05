package liadov.mypackage;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");


        

        System.out.println("\nWelcome to lesson5 HomeWork!\nPlease type \"help\"");
        readConsoleCommand();

    }

    private static void readConsoleCommand() {
        String commandTemp = "";
        boolean continueRun = true;

        Scanner scanner = new Scanner(System.in);
        while (continueRun&&scanner.hasNext()) {
            if (scanner.hasNext()) {
                commandTemp= scanner.next();
                log.info("Command read: {}",commandTemp);
            }
        }
    }
}