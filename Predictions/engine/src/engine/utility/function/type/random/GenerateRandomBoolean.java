package engine.utility.function.type.random;

import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;

public class GenerateRandomBoolean implements Function {

    @Override
    public String getName() {
        return "generateRandomBoolean";
    }

    @Override
    public Object run()
            throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        Function numGen = new GenerateRandomInteger(1);
        return (Boolean)((Integer) numGen.run() == 1);
    }
}