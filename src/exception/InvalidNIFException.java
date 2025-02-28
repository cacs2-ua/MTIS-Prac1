package exception;

public class InvalidNIFException extends RuntimeException {
    public InvalidNIFException(String message) {
        super(message);
    }
}