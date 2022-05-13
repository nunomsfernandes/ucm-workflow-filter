package pt.nunomsf.ucm.components.workflow.exceptions;

public class PublishException extends Exception {

    public PublishException(String message) {
        super(message);
    }

    public PublishException(String message, Throwable cause) {
        super(message, cause);
    }
}
