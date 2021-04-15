package liadov.mypackage.lesson7.exception;

public class ExceptionHandler {
    /**
     * Method return String value of StackTrace elements of exception
     *
     * @param e      Throwable
     * @param rowNum int optional amount of rows to provide
     * @return String value of StackTrace elements of exception
     */
    public static String getFullStackTrace(Throwable e, int... rowNum) {
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        int startingPoint = 0;
        if (rowNum.length > 0) {
            startingPoint = stackTraceElements.length - rowNum[0];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(e.toString());
        for (int i = startingPoint; i < stackTraceElements.length; i++) {
            stringBuilder.append("\n");
            stringBuilder.append(e.getStackTrace()[i].toString());
        }
        if (startingPoint > 0) {
            stringBuilder.append("\n");
            stringBuilder.append("...and ");
            stringBuilder.append(startingPoint);
            stringBuilder.append(" more getStackTrace rows not printed\n");
        }
        return stringBuilder.toString();
    }
}
