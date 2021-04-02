package liadov.mypackage.lesson4;

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
}
