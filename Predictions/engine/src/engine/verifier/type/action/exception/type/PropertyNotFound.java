package engine.verifier.type.action.exception.type;

public class PropertyNotFound extends Exception {
    private final String actionName;
    private final String entityName;
    private final String propertyName;

    public PropertyNotFound(String actionName, String entityName, String propertyName) {
        this.actionName = actionName;
        this.entityName = entityName;
        this.propertyName = propertyName;
    }

    @Override
    public String getMessage() {
        return "Could not find a property named " + propertyName + " within entity " + entityName + "'s properties," +
                " in action " + actionName;
    }
}
