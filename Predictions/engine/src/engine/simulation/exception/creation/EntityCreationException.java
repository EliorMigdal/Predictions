package engine.simulation.exception.creation;

public class EntityCreationException extends Exception {
    private final String entityName;
    private final String message;

    public EntityCreationException(String entityName, String message) {
        this.entityName = entityName;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "Exception while creating entity " + entityName + ": " + message;
    }
}
