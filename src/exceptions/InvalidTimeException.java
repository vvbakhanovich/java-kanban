package exceptions;

public class InvalidTimeException extends RuntimeException{

    public InvalidTimeException() {
        super();
    }

    public InvalidTimeException(String message) {
        super(message);
    }

    public InvalidTimeException(Exception cause) {
        super(cause);
    }
}
