package liadov.mypackage.lesson4;

import java.io.IOException;

public class MyCheckedException extends IOException {
    public MyCheckedException() {
        super();
    }

    public MyCheckedException(String message) {
        super(message);
    }

    public MyCheckedException(String message, Throwable cause) {
        super(message, cause);
    }

}
