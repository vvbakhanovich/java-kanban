package exceptions;

public class RegistrationException extends RuntimeException{
    public RegistrationException() {
        super();
    }

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(Exception cause) {
        super(cause);
    }
}
