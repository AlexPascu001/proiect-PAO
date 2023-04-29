package exceptions;

public class IllegalAgeException extends Exception {
    public IllegalAgeException() {
        super("Age must be over 18");
    }
}
