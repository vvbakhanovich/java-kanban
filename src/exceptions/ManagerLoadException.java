package exceptions;

public class ManagerLoadException extends RuntimeException{

    public ManagerLoadException() {
        super();
    }

    public ManagerLoadException(String message) {
        super(message);
    }

    public ManagerLoadException(Exception cause) {
        super(cause);
    }
}
