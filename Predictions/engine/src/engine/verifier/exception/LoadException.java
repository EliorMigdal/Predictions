package engine.verifier.exception;

public class LoadException extends Exception {
    private final String message;

    public LoadException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "Exception while loading XML file:\n" + message;
    }
}