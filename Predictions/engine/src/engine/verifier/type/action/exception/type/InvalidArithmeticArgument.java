package engine.verifier.type.action.exception.type;

public class InvalidArithmeticArgument extends Exception {
    private final String actionName;
    private final String argument;

    public InvalidArithmeticArgument(String actionName, String argument) {
        this.actionName = actionName;
        this.argument = argument;
    }

    @Override
    public String getMessage() {
        return "Argument " + argument + " provided for " + actionName + " is not arithmetic!";
    }
}
