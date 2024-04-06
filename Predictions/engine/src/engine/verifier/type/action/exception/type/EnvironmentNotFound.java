package engine.verifier.type.action.exception.type;

public class EnvironmentNotFound extends Exception {
    private final String actionName;
    private final String environmentName;

    public EnvironmentNotFound(String actionName, String environmentName) {
        this.actionName = actionName;
        this.environmentName = environmentName;
    }

    @Override
    public String getMessage() {
        return "Environment " + environmentName + " was not found in action " + actionName;
    }
}
