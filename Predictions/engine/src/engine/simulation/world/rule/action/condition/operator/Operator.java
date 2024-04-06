package engine.simulation.world.rule.action.condition.operator;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;

import java.io.Serializable;

public interface Operator extends Serializable {
    boolean evaluateOperation(Object firstArgument, Object secondArgument) throws IncompatibleTypes;
    String getOperatorSign();
}