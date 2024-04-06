package engine.simulation.exception.creation;

public class RuleCreationException extends Exception {
    private final String ruleName;
    private final String message;

    public RuleCreationException(String ruleName, String message) {
        this.ruleName = ruleName;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "Exception while creating rule " + ruleName + ": " + message;
    }
}