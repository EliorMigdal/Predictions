package engine.simulation.world.property.range.exception;

public class RangeValueException extends Exception{
    private Object maxValue;

    public RangeValueException(Object maxValue) {
        setMaxValue(maxValue);
    }

    public Object getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Object maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public String getMessage() {
        return "Minimal value inserted is higher than the maximal value - " + getMaxValue();
    }
}