package engine.simulation.exception.runtime;

public class SimulationRuntimeException extends Exception {
    private final String message;

    public SimulationRuntimeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "Simulation runtime exception: " + message;
    }
}