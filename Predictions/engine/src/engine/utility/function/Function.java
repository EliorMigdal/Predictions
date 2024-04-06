package engine.utility.function;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

import java.io.Serializable;

public interface Function extends Serializable {
    String getName();
    Object run() throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity;
}