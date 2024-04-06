package engine.simulation.world.rule.action.condition.operator.type;

import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.condition.operator.Operator;

public class BiggerThan implements Operator {
    public BiggerThan() {}

    @Override
    public boolean evaluateOperation(Object propertyValue, Object conditionValue)
            throws IncompatibleTypes {
        if (propertyValue instanceof Integer && conditionValue instanceof Integer) {
            return ((Integer) propertyValue).compareTo((Integer) conditionValue) > 0;
        } else if (propertyValue instanceof Float && conditionValue instanceof Float) {
            return ((Float) propertyValue).compareTo((Float) conditionValue) > 0;
        } else if (propertyValue instanceof Float && conditionValue instanceof Integer) {
            return ((Float) propertyValue).compareTo(((Integer) conditionValue).floatValue()) > 0;
        } else if (propertyValue instanceof Integer && conditionValue instanceof Float) {
            return ((Integer) propertyValue).compareTo(((Float) conditionValue).intValue()) > 0;
        } else {
            throw new IncompatibleTypes(propertyValue.getClass().
                    getSimpleName(), conditionValue.getClass().getSimpleName());
        }
    }

    @Override
    public String getOperatorSign() {
        return ">";
    }
}