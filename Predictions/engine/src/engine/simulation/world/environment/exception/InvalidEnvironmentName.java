package engine.simulation.world.environment.exception;

public class InvalidEnvironmentName extends Exception {
    private String environmentName;

    public InvalidEnvironmentName(String environmentName) {
        setEnvironmentName(environmentName);
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    @Override
    public String getMessage() {
        return "There is no environment variable named -" + environmentName + " in our system!";
    }
}