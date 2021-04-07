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
            sb.append(command.DESCRIPTION);
        }
        System.out.println(sb);
    }
}