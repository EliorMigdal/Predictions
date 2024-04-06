package engine.verifier.type.action.exception.type;

public class InvalidCount extends Exception {
    private final String secondaryEntity;
    private final String count;

    public InvalidCount(String secondaryEntity, String count) {
        this.secondaryEntity = secondaryEntity;
        this.count = count;
    }

    @Override
    public String getMessage() {
        return "Invalid count of " + count + " in secondary entity " + secondaryEntity;
    }
}
