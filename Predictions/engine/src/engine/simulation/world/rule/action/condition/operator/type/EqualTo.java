package engine.simulation.world.rule.action.condition.operator.type;

import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.condition.operator.Operator;

public class EqualTo implements Operator {
    public EqualTo() {}

    @Override
    public boolean evaluateOperation(Object firstArgument, Object secondArgument) throws IncompatibleTypes {
        if (firstArgument instanceof Integer && secondArgument instanceof Integer) {
            return ((Integer)firstArgument).compareTo((Integer)secondArgument) == 0;
        } else if (firstArgument instanceof Float && secondArgument instanceof Float) {
            return ((Float)firstArgument).compareTo((Float)secondArgument) == 0;
        } else if (firstArgument instanceof Integer && secondArgument instanceof Float) {
            return ((Integer) firstArgument).compareTo(((Float) secondArgument).intValue()) == 0;
        } else if (firstArgument instanceof Float && secondArgument instanceof Integer) {
            return ((Float) firstArgument).compareTo(((Integer) secondArgument).floatValue()) == 0;
        } else if (firstArgument instanceof Boolean && secondArgument instanceof Boolean) {
            return ((Boolean)firstArgument).compareTo((Boolean)secondArgument) == 0;
        } else if (firstArgument instanceof String && secondArgument instanceof String) {
            return ((String) firstArgument).compareTo((String) secondArgument) == 0;
        } else {
            throw new IncompatibleTypes(firstArgument.getClass().
                    getSimpleName(), secondArgument.getClass().getSimpleName());
        }
    }

    @Override
    public String getOperatorSign() {
        return "=";
    }
}