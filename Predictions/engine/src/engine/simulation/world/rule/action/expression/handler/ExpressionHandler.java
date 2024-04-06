package engine.simulation.world.rule.action.expression.handler;

import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

public interface ExpressionHandler {
    Object handleExpression(String expression) throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity;
}
