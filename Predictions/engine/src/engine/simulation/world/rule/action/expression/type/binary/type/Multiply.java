package engine.simulation.world.rule.action.expression.type.binary.type;

import engine.simulation.world.rule.action.expression.type.binary.Arithmetic;

public class Multiply implements Arithmetic {

    public Multiply() {}

    @Override
    public Object calculate(Object firstArgument, Object secondArgument) {
        if (firstArgument instanceof Integer && secondArgument instanceof Integer) {
            return (Integer) firstArgument * (Integer) secondArgument;
        } else if (firstArgument instanceof Float && secondArgument instanceof Float) {
            return (Float) firstArgument * (Float) secondArgument;
        } else {
            return null;
        }
    }

    @Override
    public String getOperationName() {
        return "Multiply";
    }
}