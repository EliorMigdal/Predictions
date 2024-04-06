package engine.simulation.world.rule.action.expression;

import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

import java.io.Serializable;

public interface Expression extends Serializable {
    Object evaluate(Instance mainInstance)
            throws NumberFormatException, IncompatibleTypes, InvalidPropertyReference, InvalidEntity;
    String getExpression();
}