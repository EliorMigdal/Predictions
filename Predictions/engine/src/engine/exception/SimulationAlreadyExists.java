package engine.exception;

public class SimulationAlreadyExists extends Exception {
    private final String simulationName;

    public SimulationAlreadyExists(String simulationName) {
        this.simulationName = simulationName;
    }

    @Override
    public String getMessage() {
        return "Exception: simulation named " + this.simulationName + " already exists in our system!";
    }
}
