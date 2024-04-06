package engine.simulation.world.rule.activation.exception;

public class InvalidProbability extends Exception {
    private double probability;

    public InvalidProbability(double probability) {
        setProbability(probability);
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String getMessage() {
        return "Invalid probability value inserted - " + getProbability();
    }
}