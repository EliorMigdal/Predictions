package engine.utility.function.type.max;

import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;

import java.util.LinkedHashMap;

public class ExtractMaxKeyFromMap implements Function {
    private final LinkedHashMap<Integer, ?> argument;

    public ExtractMaxKeyFromMap(LinkedHashMap<Integer, ?> argument) {
        this.argument = argument;
    }

    @Override
    public String getName() {
        return "extractMaxKeyFromMap";
    }

    @Override
    public Object run()
            throws InvalidPropertyReference, IncompatibleTypes {
        Integer max = 0;

        for (Integer key : argument.keySet()) {
            if (key > max) {
                max  = key;
            }
        }

        return max;
    }
}