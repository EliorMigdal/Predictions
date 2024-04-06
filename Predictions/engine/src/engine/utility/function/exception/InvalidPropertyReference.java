package engine.utility.function.exception;

public class InvalidPropertyReference extends Exception {
    private final String propertyName;
    private final String entityName;

    public InvalidPropertyReference(String propertyName, String entityName) {
        this.propertyName = propertyName;
        this.entityName = entityName;
    }

    @Override
    public String getMessage() {
        return "Entity " + entityName + " has no property " + propertyName + "!";
    }
}