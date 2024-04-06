package engine.simulation.world.rule.action.condition.exception;

public class IncompatibleTypes extends Exception {
    private String firstType, secondType;

    public IncompatibleTypes(String firstType, String secondType) {
        setFirstType(firstType);
        setSecondType(secondType);
    }

    public String getFirstType() {
        return firstType;
    }

    public void setFirstType(String firstType) {
        this.firstType = firstType;
    }

    public String getSecondType() {
        return secondType;
    }

    public void setSecondType(String secondType) {
        this.secondType = secondType;
    }

    @Override
    public String getMessage() {
        return "Incompatible types inserted - type "
                + getFirstType() + " and type " + getSecondType();
    }
}