package liadov.mypackage.lesson4;

/**
 * Thrown to indicate that an array has been accessed with an illegal index.
 * The index is either negative or greater than or equal to the size of the array.
 */
public class ElementDoesNotExistException extends ArrayIndexOutOfBoundsException {

    /**
     * Constructs an ElementDoesNotExistException with no detail message.
     */
    public ElementDoesNotExistException() {
        super();
    }

    /**
     * Constructs a new ElementDoesNotExistException class with an argument indicating the illegal index.
     *
     * @param index - the illegal index
     */
    public ElementDoesNotExistException(int index) {
        super(index);
    }

    /**
     * Constructs an ElementDoesNotExistException class with the specified detail message.
     *
     * @param s - the detail message
     */
    public ElementDoesNotExistException(String s) {
        super(s);
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
