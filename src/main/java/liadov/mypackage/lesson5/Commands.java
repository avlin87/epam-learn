package liadov.mypackage.lesson5;

public enum Commands {
    ADD("ADD (example: add 5 fileName \"text\")"),
    DELETE("D (example: delete 5 fileName)"),
    PRINT("P (print 4 fileName)"),
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