package engine.verifier.type.exception;

public class XMLException extends Exception {
    private final Exception exception;

    public XMLException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        return "Exception loading XML file: " + exception.getMessage();
    }
}
