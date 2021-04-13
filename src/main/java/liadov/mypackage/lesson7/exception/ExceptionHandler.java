package liadov.mypackage.lesson7.exception;

public class ExceptionHandler {
    /**
     * Method return String value of StackTrace elements of exception
     * @param e Throwable
     * @return String value of StackTrace elements of exception
     */
    public static String getFullStackTrace(Throwable e){
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement element:e.getStackTrace()) {
            stringBuilder.append(element.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
