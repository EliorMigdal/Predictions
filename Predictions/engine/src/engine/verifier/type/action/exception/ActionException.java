package engine.verifier.type.action.exception;

public class ActionException extends Exception {
    private final Exception exception;

    public ActionException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        return "Action exception: " + exception.getMessage();
    }
}
