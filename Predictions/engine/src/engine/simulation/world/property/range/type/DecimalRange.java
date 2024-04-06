package engine.simulation.world.property.range.type;

import engine.jaxb.generated.PRDRange;
import engine.simulation.world.property.range.Range;
import engine.simulation.world.property.range.exception.RangeValueException;

public class DecimalRange implements Range, Cloneable {
    private Integer minValue;
    private Integer maxValue;

    public DecimalRange(PRDRange prdRange) throws RangeValueException {
        if (prdRange.getFrom() > prdRange.getTo()) {
            throw new RangeValueException((int) prdRange.getTo());
        } else {
            setMinValue((int)prdRange.getFrom());
            setMaxValue((int)prdRange.getTo());
        }
    }

    public DecimalRange(Integer minValue, Integer maxValue) throws RangeValueException {
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
        this.minValue = (Integer) minValue;
    }

    @Override
    public Object getMaxValue() {
        return maxValue;
    }

    @Override
    public void setMaxValue(Object maxValue) {
        this.maxValue = (Integer) maxValue;
    }

    @Override
    public Range clone() {
        try {
            DecimalRange clone = (DecimalRange) super.clone();
            clone.setMinValue(this.minValue);
            clone.setMaxValue(this.maxValue);
            return clone;
        } catch (CloneNotSupportedException error) {
            throw new AssertionError(error);
        }
    }
}