package engine.simulation.exception.creation;

public class EnvironmentCreationException extends Exception {
    private final String environmentName;
    private final String message;

    public EnvironmentCreationException(String environmentName, String message) {
        this.environmentName = environmentName;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "Exception while creating environment variable " + environmentName + ": " + message;
    }
}