package engine.simulation.world.entity.instance.exception;

public class PropertyAlreadyExists extends Exception {
    private String propertyName;

    public PropertyAlreadyExists(String environmentName) {
        setEnvironmentName(environmentName);
    }

    public String getEnvironmentName() {
        return propertyName;
    }

    public void setEnvironmentName(String environmentName) {
        this.propertyName = environmentName;
    }

    @Override
    public String getMessage() {
        return "Exception: Property named " + propertyName + " already exists in our system!";
    }

}