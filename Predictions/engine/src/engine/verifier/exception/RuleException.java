package engine.verifier.exception;

public class RuleException extends Exception {
    private final String ruleName;
    private final Exception actionException;

    public RuleException(String ruleName, Exception actionException) {
        this.ruleName = ruleName;
        this.actionException = actionException;
    }

    @Override
    public String getMessage() {
        return "In rule " + '"'+ruleName+'"' + ": " + actionException.getMessage();
    }
}
