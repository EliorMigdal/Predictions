package engine.simulation.world.rule.activation;

import engine.simulation.world.environment.exception.InvalidEnvironmentName;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

import java.io.Serializable;

public interface Activation extends Serializable {
    boolean checkForActivation(Integer tickNumber)
            throws InvalidEnvironmentName, InvalidPropertyReference, IncompatibleTypes, InvalidEntity;
    Object getActivationTerm();
    String getTermName();
}