package liadov.mypackage.lesson4;

/**
 * Thrown to indicate that an array has been accessed with an illegal index.
 * The index is either negative or greater than or equal to the size of the array.
 */
public class MyUncheckedArrayIndexOutOfBoundsException extends ArrayIndexOutOfBoundsException {
    public MyUncheckedArrayIndexOutOfBoundsException() {
        super();
    }

    public MyUncheckedArrayIndexOutOfBoundsException(int index) {
        super(index);
    }

    public MyUncheckedArrayIndexOutOfBoundsException(String s) {
        super(s);
    }

    public String getFullStackTrace(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.toString());
        for (StackTraceElement element:super.getStackTrace()) {
            sb.append("\n\t"+element.toString());
        }
        return sb.toString();
    }
}
