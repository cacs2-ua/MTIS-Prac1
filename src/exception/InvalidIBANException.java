package exception;

public class InvalidIBANException extends RuntimeException {
    public InvalidIBANException(String message) {
        super(message);
    }
}
