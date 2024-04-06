package engine.simulation.world.property.range.type;

import engine.jaxb.generated.PRDRange;
import engine.simulation.world.property.range.Range;
import engine.simulation.world.property.range.exception.RangeValueException;

public class FloatRange implements Range, Cloneable {
    private Float minValue;
    private Float maxValue;

    public FloatRange(PRDRange prdRange) throws RangeValueException {
        if (prdRange.getFrom() > prdRange.getTo()) {
            throw new RangeValueException(prdRange.getTo());
        } else {
            setMinValue(Double.valueOf(prdRange.getFrom()).floatValue());
            setMaxValue(Double.valueOf(prdRange.getTo()).floatValue());
        }
    }

    public FloatRange(Float minValue, Float maxValue) throws RangeValueException {
        if (minValue > maxValue) {
            throw new RangeValueException(maxValue);
        } else {
            setMinValue(minValue);
            setMaxValue(maxValue);
        }
    }

    @Override
    public Object getMinValue() {
        return minValue;
    }

    @Override
    public void setMinValue(Object minValue) {
        this.minValue = (Float)minValue;
    }

    @Override
    public Object getMaxValue() {
        return maxValue;
    }

    @Override
    public void setMaxValue(Object maxValue) {
        this.maxValue = (Float) maxValue;
    }

    @Override
    public Range clone() {
        try {
            FloatRange clone = (FloatRange) super.clone();
            clone.setMinValue(this.minValue);
            clone.setMaxValue(this.maxValue);
            return clone;
        } catch (CloneNotSupportedException error) {
            throw new AssertionError(error);
        }
    }
}