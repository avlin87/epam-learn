package liadov.mypackage.lesson5.exceptions;

/**
 * Exception thrown in case requested file row is not present in target file
 */
public class UnreachableRequestedRow extends RuntimeException {
    public UnreachableRequestedRow() {
    }
}