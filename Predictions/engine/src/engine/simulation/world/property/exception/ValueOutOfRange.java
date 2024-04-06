package engine.simulation.world.property.exception;

public class ValueOutOfRange extends Exception{
    private Object minValue;
    private Object maxValue;

    public Object getMinValue() {
        return minValue;
    }

    public void setMinValue(Object minValue) {
        this.minValue = minValue;
    }

    public Object getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Object maxValue) {
        this.maxValue = maxValue;
    }

    public ValueOutOfRange(Object minValue, Object maxValue) {
        setMinValue(minValue);
        setMaxValue(maxValue);
    }

    @Override
    public String getMessage() {
        return "Invalid value - Out of property's range, from " +
                getMinValue() + " to " + getMaxValue() + ".";
    }
}