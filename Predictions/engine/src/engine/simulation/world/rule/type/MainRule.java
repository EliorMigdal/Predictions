package engine.simulation.world.rule.type;

import engine.jaxb.generated.PRDAction;
import engine.jaxb.generated.PRDActivation;
import engine.jaxb.generated.PRDRule;
import engine.simulation.world.entity.Entity;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.environment.exception.InvalidEnvironmentName;
import engine.simulation.world.rule.Rule;
import engine.simulation.world.rule.action.Action;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.simulation.world.rule.action.factory.ActionGenerator;
import engine.simulation.world.rule.activation.Activation;
import engine.simulation.world.rule.activation.exception.InvalidProbability;
import engine.simulation.world.rule.activation.exception.InvalidTicksValue;
import engine.simulation.world.rule.activation.type.Probability;
import engine.simulation.world.rule.activation.type.Ticks;
import engine.utility.function.exception.InvalidPropertyReference;

import java.util.ArrayList;
import java.util.List;

public class MainRule implements Rule {
    private String ruleName;
    private final ArrayList<Activation> activationTerms;
    private final ArrayList<Action> actions;

    public MainRule(PRDRule prdRule, ArrayList<Entity> entityCollection, Environment environment)
            throws InvalidTicksValue, InvalidProbability {
        setRuleName(prdRule.getName());
        this.actions = new ArrayList<>();
        this.activationTerms = new ArrayList<>();

        Activation probabilityTerm = new Probability(), ticksTerm = new Ticks();
        if (prdRule.getPRDActivation() != null) {
            PRDActivation prdActivation = prdRule.getPRDActivation();

            if (prdActivation.getProbability() != null) {
                probabilityTerm = new Probability(prdActivation.getProbability());
            }

            if (prdActivation.getTicks() != null) {
                ticksTerm = new Ticks(prdActivation.getTicks());
            }
        }
        addActivation(probabilityTerm);
        addActivation(ticksTerm);

        List<PRDAction> prdActions = prdRule.getPRDActions().getPRDAction();
        ActionGenerator actionGenerator = new ActionGenerator();

        for (PRDAction prdAction : prdActions) {
            Action newAction = actionGenerator.generateAction(prdAction, entityCollection, environment);
            this.actions.add(newAction);
        }
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public ArrayList<Activation> getActivationTerms() {
        return activationTerms;
    }

    @Override
    public boolean checkForActivation(Integer tickNumber)
            throws InvalidEnvironmentName, InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        for (Activation activation : this.activationTerms) {
            if (!activation.checkForActivation(tickNumber)) {
                return false;
            }
        }

        return true;
    }

    public void addActivation(Activation activationTerm) {
        this.activationTerms.add(activationTerm);
    }

    public ArrayList<Action> getActions() {
        return actions;
    }
}