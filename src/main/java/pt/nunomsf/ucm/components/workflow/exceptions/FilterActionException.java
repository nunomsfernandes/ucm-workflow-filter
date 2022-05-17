package pt.nunomsf.ucm.components.workflow.exceptions;

public class FilterActionException extends RuntimeException {

    public FilterActionException(String message) {
        super(message);
    }

    public FilterActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterActionException(Throwable cause) {
        super(cause);
    }
}
