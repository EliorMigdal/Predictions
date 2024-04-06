package engine.simulation.world.entity.instance.exception;

public class PropertyNotFound extends Exception {
    private String propertyName;

    public PropertyNotFound(String environmentName) {
        setEnvironmentName(environmentName);
    }

    public void setEnvironmentName(String environmentName) {
        this.propertyName = environmentName;
    }

    @Override
    public String getMessage() {
        return "Exception: There is no property named " + propertyName + " in our system!";
    }

}