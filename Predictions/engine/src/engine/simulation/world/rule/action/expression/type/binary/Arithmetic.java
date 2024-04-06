package engine.simulation.world.rule.action.expression.type.binary;

import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.type.binary.exception.DivideByZero;

import java.io.Serializable;

public interface Arithmetic extends Serializable {
    Object calculate(Object firstArgument, Object secondArgument)
            throws IncompatibleTypes, DivideByZero;
    String getOperationName();
}