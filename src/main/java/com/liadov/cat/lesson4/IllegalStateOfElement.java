package com.liadov.cat.lesson4;

/**
 * Signals that an attempt to access Element failed
 */
public class IllegalStateOfElement extends RuntimeException {

    public IllegalStateOfElement(String message) {
        super(message);
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
