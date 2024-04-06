package engine.simulation.world.rule;

import engine.simulation.world.environment.exception.InvalidEnvironmentName;
import engine.simulation.world.rule.action.Action;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.simulation.world.rule.activation.Activation;
import engine.utility.function.exception.InvalidPropertyReference;

import java.io.Serializable;
import java.util.ArrayList;

public interface Rule extends Serializable {
    String getRuleName();
    ArrayList<Action> getActions();
    ArrayList<Activation> getActivationTerms();
    boolean checkForActivation(Integer tickNumber)
            throws InvalidEnvironmentName, InvalidPropertyReference, IncompatibleTypes, InvalidEntity;
}