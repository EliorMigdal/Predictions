package engine.simulation.world.rule.exception;

public class RuleException extends Exception {
    private final String ruleName;
    private final String message;

    public RuleException(String ruleName, String message) {
        this.ruleName = ruleName;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "In rule " + ruleName + ": " + message;
    }
}