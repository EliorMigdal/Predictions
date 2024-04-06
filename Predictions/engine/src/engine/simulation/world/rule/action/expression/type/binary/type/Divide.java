package engine.simulation.world.rule.action.expression.type.binary.type;

import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.type.binary.Arithmetic;
import engine.simulation.world.rule.action.expression.type.binary.exception.DivideByZero;

public class Divide implements Arithmetic {

    public Divide() {}

    @Override
    public Object calculate(Object firstArgument, Object secondArgument)
            throws IncompatibleTypes, DivideByZero {
        if (firstArgument instanceof Float && secondArgument instanceof Float) {
            if ((Float) secondArgument == 0) {
                throw new DivideByZero();
            } else {
                return (Float) firstArgument / (Float) secondArgument;
            }
        } else {
            throw new IncompatibleTypes(firstArgument.getClass().getSimpleName(),
                    secondArgument.getClass().getSimpleName());
        }
    }

    @Override
    public String getOperationName() {
        return "Divide";
    }
}