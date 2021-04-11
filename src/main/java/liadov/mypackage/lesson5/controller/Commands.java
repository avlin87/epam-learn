package liadov.mypackage.lesson5.controller;

public enum Commands {
    ADD("example: add 5 fileName.txt \"text\""),
    DELETE("example: delete 1 fileName.txt)"),
    PRINT("print 4 fileName.txt"),
    HELP("see list of available commands"),
    EXIT("finish current program");

    private final String DESCRIPTION;

    Commands(String value) {
        this.DESCRIPTION = value;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }
}