package engine.verifier.type.action.exception.type;

public class InvalidMode extends Exception {
    private final String modeName;

    public InvalidMode(String modeName) {
        this.modeName = modeName;
    }

    @Override
    public String getMessage() {
        return "Cannot create replace action with mode " + modeName;
    }
}
