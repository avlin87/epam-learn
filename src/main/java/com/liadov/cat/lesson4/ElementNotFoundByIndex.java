package com.liadov.cat.lesson4;

/**
 * Thrown to indicate that an array has been accessed with an illegal index.
 */
public class ElementNotFoundByIndex extends ArrayIndexOutOfBoundsException {

    /**
     * Constructs a new ElementDoesNotExistException class with an argument indicating the illegal index.
     *
     * @param index - the illegal index
     */
    public ElementNotFoundByIndex(int index) {
        super(index);
    }

    /**
     * Method return String message formed from StackTraceElements of Exception
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        for (StackTraceElement element : super.getStackTrace()) {
            sb.append("\n\t").append(element.toString());
        }
        return sb.toString();
    }
}
