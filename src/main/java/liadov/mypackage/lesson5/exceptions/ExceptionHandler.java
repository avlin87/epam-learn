package liadov.mypackage.lesson5.exceptions;

public class ExceptionHandler {
    public static String getStackTrace(Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString());
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append("\n\t" + element.toString());
        }
        return sb.toString();
    }
}