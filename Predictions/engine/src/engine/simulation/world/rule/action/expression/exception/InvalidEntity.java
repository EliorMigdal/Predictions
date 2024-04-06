package engine.simulation.world.rule.action.expression.exception;

public class InvalidEntity extends Exception {
    private final String mainEntity;
    private final String argumentEntity;

    public InvalidEntity(String mainEntity, String argumentEntity) {
        this.mainEntity = mainEntity;
        this.argumentEntity = argumentEntity;
    }

    @Override
    public String getMessage() {
        return "Expression error: got entity " + argumentEntity + ", expected entity " + mainEntity;
    }
}
