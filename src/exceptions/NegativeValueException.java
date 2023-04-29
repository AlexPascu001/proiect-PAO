package exceptions;

public class NegativeValueException extends Exception {
    public NegativeValueException() {
        super("Negative value not allowed");
    }
}
