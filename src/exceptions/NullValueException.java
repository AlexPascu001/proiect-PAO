package exceptions;

public class NullValueException extends Exception {
    public NullValueException() {
        super("Null value not allowed");
    }
}
