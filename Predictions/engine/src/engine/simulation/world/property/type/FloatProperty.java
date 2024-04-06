package engine.simulation.world.property.type;

import engine.jaxb.generated.PRDEnvProperty;
import engine.jaxb.generated.PRDProperty;
import engine.simulation.world.property.Property;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.property.range.Range;
import engine.simulation.world.property.range.exception.RangeValueException;
import engine.simulation.world.property.range.type.FloatRange;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;
import engine.utility.function.type.max.ExtractMaxKeyFromMap;
import engine.utility.function.type.random.GenerateRandomFloat;
import engine.utility.function.type.random.GenerateRandomInteger;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class FloatProperty implements Property, Cloneable {
    private String propertyName;
    private Float propertyValue;
    private String propertyType;
    private Range propertyRange = null;
    private Boolean randomlyInitialized;
    private Integer ticksSinceLastUpdate = 0;
    private LinkedHashMap<Integer, Float> propertyHistory;

    public FloatProperty(PRDProperty PRDProperty)
            throws RangeValueException, InvalidPropertyReference, IncompatibleTypes,
            InvalidPropertyType, ValueOutOfRange, InvalidEntity {
        setPropertyName(PRDProperty.getPRDName());
        setRandomlyInitialized(PRDProperty.getPRDValue().isRandomInitialize());
        this.propertyHistory = new LinkedHashMap<>();
        this.propertyType = PRDProperty.getType();
        Object value;
        Function numberGen;

        if (PRDProperty.getPRDRange() != null) {
            setPropertyRange(new FloatRange(PRDProperty.getPRDRange()));
        }

        if (this.hasRange() && this.randomlyInitialized) {
            Float max = (Float) this.propertyRange.getMaxValue();
            Float min = (Float) this.propertyRange.getMinValue();
            numberGen = new GenerateRandomFloat(max, min);
            value = numberGen.run();
        } else if (!this.hasRange() && this.randomlyInitialized) {
            numberGen = new GenerateRandomFloat(32768);
            value = numberGen.run();
        } else {
            value = Float.parseFloat(PRDProperty.getPRDValue().getInit());
        }

        setPropertyValue(value);
    }

    public FloatProperty(PRDEnvProperty PRDEnvProperty)
            throws RangeValueException, InvalidPropertyType, ValueOutOfRange,
            InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        setPropertyName(PRDEnvProperty.getPRDName());
        this.propertyHistory = new LinkedHashMap<>();
        this.propertyType = PRDEnvProperty.getType();
        Object randomInitialValue;
        Function numberGen;

        if (PRDEnvProperty.getPRDRange() != null) {
            Range range = new FloatRange(PRDEnvProperty.getPRDRange());
            setPropertyRange(range);
            numberGen = new GenerateRandomFloat(((Double) PRDEnvProperty.getPRDRange().getTo()).floatValue(),
                    ((Double) PRDEnvProperty.getPRDRange().getFrom()).floatValue());

        } else {
            numberGen = new GenerateRandomFloat(32768);
        }

        randomInitialValue = numberGen.run();

        setPropertyValue(randomInitialValue);
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public Object getPropertyValue() {
        return propertyValue;
    }

    @Override
    public void setPropertyValue(Object object)
            throws InvalidPropertyType, ValueOutOfRange, InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        Object oldValue = this.propertyValue;
        if (object instanceof Float) {
            if (hasRange()) {
                if ((Float) object < (Float) propertyRange.getMinValue() ||
                        (Float) object > (Float) propertyRange.getMaxValue()) {
                    throw new ValueOutOfRange(propertyRange.getMinValue(), propertyRange.getMaxValue());
                }
            }
            this.propertyValue = (Float) object;
        } else if (object instanceof Integer) {
            if (hasRange()) {
                if ((Float) ((Integer)object).floatValue() < (Float) propertyRange.getMinValue() ||
                        (Float) ((Integer)object).floatValue() > (Float) propertyRange.getMaxValue()) {
                    throw new ValueOutOfRange(propertyRange.getMinValue(), propertyRange.getMaxValue());
                }
            }
            this.propertyValue = ((Integer)object).floatValue();
        } else {
            throw new InvalidPropertyType(object.getClass().getSimpleName(), "Float");
        }

        if (!this.propertyValue.equals(oldValue)) {
            resetTicksCounter();
        }

        if (this.propertyHistory != null) {
            Function getMax = new ExtractMaxKeyFromMap(this.propertyHistory);
            this.updateHistory((Integer) getMax.run());
        } else {
            this.updateHistory(0);
        }
    }

    @Override
    public Range getPropertyRange() {
        return propertyRange;
    }

    public void setPropertyRange(Range propertyRange)
            throws RangeValueException {
        this.propertyRange = new FloatRange((Float) propertyRange.getMinValue(), (Float) propertyRange.getMaxValue());
    }

    @Override
    public Boolean getRandomlyInitialized() {
        return randomlyInitialized;
    }

    @Override
    public void setRandomlyInitialized(Boolean randomlyInitialized) {
        this.randomlyInitialized = randomlyInitialized;
    }

    @Override
    public Boolean hasRange() {
        return propertyRange != null;
    }

    @Override
    public Property clone(Integer currentTick) {
        try {
            FloatProperty clone = (FloatProperty) super.clone();
            clone.propertyName = this.propertyName;
            clone.propertyType = this.propertyType;
            clone.randomlyInitialized = this.randomlyInitialized;
            clone.ticksSinceLastUpdate = 0;
            clone.propertyHistory = new LinkedHashMap<>();

            if (this.hasRange()) {
                clone.propertyRange = this.propertyRange.clone();
            }

            if (this.randomlyInitialized && this.propertyHistory.size() == 1) {
                Function generateRandomFloat;
                if (clone.hasRange()) {
                    generateRandomFloat = new GenerateRandomFloat((Float) clone.propertyRange.getMaxValue(),
                            (Float) clone.propertyRange.getMinValue());
                } else {
                    generateRandomFloat = new GenerateRandomInteger(32768);
                }
                clone.propertyValue = (Float) generateRandomFloat.run();
            } else {
                clone.propertyValue = this.propertyValue;
                clone.propertyHistory.put(currentTick, this.propertyValue);
            }
            return clone;
        } catch (CloneNotSupportedException | InvalidPropertyReference | IncompatibleTypes | InvalidEntity exception) {
            throw new AssertionError(exception);
        }
    }

    @Override
    public Property clone() {
        try {
            FloatProperty clonedProperty = (FloatProperty) super.clone();
            clonedProperty.propertyHistory = new LinkedHashMap<>(this.propertyHistory);
            return clonedProperty;
        } catch (CloneNotSupportedException  exception) {
            throw new AssertionError(exception);
        }
    }

    @Override
    public void randomise()
            throws InvalidPropertyType, ValueOutOfRange, InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        Function generateRandomFloat;

        if (this.hasRange()) {
            generateRandomFloat = new GenerateRandomFloat((Float) this.propertyRange.getMaxValue(),
                    (Float) this.propertyRange.getMinValue());
        } else {
            generateRandomFloat = new GenerateRandomFloat(32768);
        }

        this.setPropertyValue(generateRandomFloat.run());
    }

    @Override
    public void increaseTicksCount() {
        this.ticksSinceLastUpdate++;
    }

    @Override
    public void resetTicksCounter() {
        this.ticksSinceLastUpdate = 0;
    }

    @Override
    public Integer ticksSinceLastUpdate() {
        return this.ticksSinceLastUpdate;
    }

    @Override
    public void updateHistory(Integer currentTick) {
        this.propertyHistory.put(currentTick, this.propertyValue);
    }

    @Override
    public String getPropertyFirstTickValue() {
        return this.propertyHistory.get(0).toString();
    }

    @Override
    public Double calculateConsistency() {
        int consistencyValue = 0, numOfChanges = 0;
        Float previousValue = null, currentValue;

        for (Integer key : propertyHistory.keySet()) {
            currentValue = propertyHistory.get(key);

            if (previousValue != null && Objects.equals(currentValue, previousValue)) {
                consistencyValue++;
            } else {
                numOfChanges++;
            }

            previousValue = currentValue;
        }

        DecimalFormat format = new DecimalFormat("0.000");
        Double result;

        if (numOfChanges > 0) {
            result = (double) consistencyValue / numOfChanges;
        } else {
            result = (double) consistencyValue;
        }

        return Double.parseDouble(format.format(result));
    }

    @Override
    public Double calculateAverage() {
        Double sum = 0D;
        double amount = 0D;

        for (Integer tick : propertyHistory.keySet()) {
            sum += propertyHistory.get(tick);
            amount++;
        }

        double result = -1D;

        if (amount > 0D) {
            result = sum / amount;
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloatProperty that = (FloatProperty) o;
        return Objects.equals(propertyName, that.propertyName) && Objects.equals(propertyType, that.propertyType);
    }

    @Override
    public Map<?, ?> getPropertyHistory() {
        return this.propertyHistory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyName, propertyType);
    }
}