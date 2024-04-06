package engine.verifier.type.action.exception.type;

public class EntityNotFound extends Exception {
    private final String actionName;
    private final String entityName;

    public EntityNotFound(String actionName, String entityName) {
        this.actionName = actionName;
        this.entityName = entityName;
    }

    @Override
    public String getMessage() {
        return "Entity named " + entityName + " was not found while creating action " + actionName;
    }
}
