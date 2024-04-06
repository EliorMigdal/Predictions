package engine.service.exception;

public class InitException extends Exception {
    private final String message;

    public InitException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}