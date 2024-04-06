package engine.simulation.world.rule.activation.type;

import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.simulation.world.rule.activation.Activation;
import engine.simulation.world.rule.activation.exception.InvalidProbability;
import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;
import engine.utility.function.type.random.GenerateRandomFloat;

public class Probability implements Activation {
    private Double probability = 1.0;

    public Probability() {}

    public Probability(Double probability) throws InvalidProbability {
        setProbability(probability);
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) throws InvalidProbability {
        if (probability >= 0.0 && probability <= 1.0) {
            this.probability = probability;
        } else {
            throw new InvalidProbability(probability);
        }
    }

    @Override
    public boolean checkForActivation(Integer tickNumber)
            throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        if (this.probability == 1.0) {
            return true;
        } else {
            Function floatGen = new GenerateRandomFloat(1);
            Float randomFloat = (Float) (floatGen.run());
            return randomFloat <= this.probability;
        }
    }

    @Override
    public Object getActivationTerm() {
        return getProbability();
    }

    public String getTermName() {
        return "Probability";
    }
}