package engine.simulation.world.rule.action.expression.handler.type;

import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.simulation.world.rule.action.expression.handler.ExpressionHandler;
import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;
import engine.utility.function.type.cast.GenerateBooleanFromString;
import engine.utility.function.type.cast.GenerateNumberFromString;

public class CastHandler implements ExpressionHandler {
    @Override
    public Object handleExpression(String expression)
            throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        Function stringToNum = new GenerateNumberFromString(expression);
        Function stringToBool = new GenerateBooleanFromString(expression);

        Object castedNumber = stringToNum.run();
        Object castedBool = stringToBool.run();

        if (castedNumber != null) {
            return castedNumber;
        } else if (castedBool != null) {
            return castedBool;
        } else {
            return expression;
        }
    }
}
