package liadov.mypackage.lesson4;

/**
 * Signals that an attempt to access existing CacheElement failed
 */
public class IllegalStateOfCacheElement extends Exception {

    /**
     * Constructs an IllegalStateOfCacheElement with no detail message.
     */
    public IllegalStateOfCacheElement() {
        super();
    }

    /**
     * Constructs an ElementDoesNotExistException class with the specified detail message.
     *
     * @param message - the detail message
     */
    public IllegalStateOfCacheElement(String message) {
        super(message);
    }

    /**
     * Method return String message formed from StackTraceElements of Exception
     *
     * @return String
     */
    public String getFullStackTrace() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.toString());
        for (StackTraceElement element : super.getStackTrace()) {
            sb.append("\n\t" + element.toString());
        }
        return sb.toString();
    }

}
