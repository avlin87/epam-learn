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

    public String getFullStackTrace(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.toString());
        for (StackTraceElement element:super.getStackTrace()) {
            sb.append("\n\t"+element.toString());
        }
        return sb.toString();
    }

}
