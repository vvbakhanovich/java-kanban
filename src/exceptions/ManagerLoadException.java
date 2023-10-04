package exceptions;

public class ManagerLoadException extends RuntimeException{

    public ManagerLoadException() {
        super();
    }

    public ManagerLoadException(String message) {
        super(message);
    }

    public ManagerLoadException(Throwable cause) {
        super(cause);
    }
}
