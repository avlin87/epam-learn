package liadov.mypackage.lesson5.exceptions;

/**
 * Class intended to provide String value for Exception
 */
public class ExceptionHandler {

    /**
     * Method return String value of all available exception StackTraceElements
     *
     * @param e received exception
     * @return String
     */
    public static String getStackTrace(Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString());
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append("\n\t" + element.toString());
        }
        return sb.toString();
    }
}