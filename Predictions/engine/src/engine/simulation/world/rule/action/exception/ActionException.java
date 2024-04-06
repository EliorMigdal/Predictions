package engine.simulation.world.rule.action.exception;

public class ActionException extends Exception {
    private final String actionName;
    private final String message;

    public ActionException(String actionName, String message) {
        this.actionName = actionName;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "in action " + actionName + ": " + message;
    }
}