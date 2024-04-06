package engine.simulation.world.rule.action.condition.exception;

public class ConditionError extends Exception {
    private final String conditionValue;
    private final String propertyName;
    private final String operator;
    private final String message;

    public ConditionError(String conditionValue, String propertyName, String operator, String message) {
        this.conditionValue = conditionValue;
        this.propertyName = propertyName;
        this.operator = operator;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "In condition: " + propertyName + " " + operator + " " + conditionValue + ": " + message;
    }
}