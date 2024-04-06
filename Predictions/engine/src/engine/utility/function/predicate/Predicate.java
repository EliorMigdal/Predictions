package engine.utility.function.predicate;

import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.utility.function.exception.InvalidPropertyReference;

import java.io.Serializable;

public interface Predicate extends Serializable {
    boolean run() throws InvalidPropertyReference, IncompatibleTypes;
}