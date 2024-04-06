package engine.simulation.world.property.type;

import engine.jaxb.generated.PRDEnvProperty;
import engine.jaxb.generated.PRDProperty;
import engine.simulation.world.property.Property;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.property.range.Range;
import engine.simulation.world.property.range.exception.RangeValueException;
import engine.simulation.world.property.range.type.DecimalRange;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;
import engine.utility.function.type.max.ExtractMaxKeyFromMap;
import engine.utility.function.type.random.GenerateRandomInteger;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class DecimalProperty implements Property, Cloneable {
    private String propertyName;
    private Integer propertyValue;
    private String propertyType;
    private Range range = null;
    private Boolean randomlyInitialized;
    private Integer ticksSinceLastUpdate = 0;
    private LinkedHashMap<Integer, Integer> propertyHistory;

    public DecimalProperty(PRDProperty prdProperty)
            throws NumberFormatException, ValueOutOfRange, InvalidPropertyType, RangeValueException,
            InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        setPropertyName(prdProperty.getPRDName());
        setRandomlyInitialized(prdProperty.getPRDValue().isRandomInitialize());
        this.propertyHistory = new LinkedHashMap<>();
        this.propertyType = prdProperty.getType();
        Object value;
        Function numberGen;

        if (prdProperty.getPRDRange() != null) {
            setPropertyRange(new DecimalRange(prdProperty.getPRDRange()));
        }

        if (this.hasRange() && this.randomlyInitialized) {
            int max = (int) this.range.getMaxValue();
            int min = (int) this.range.getMinValue();
            numberGen = new GenerateRandomInteger(max, min);
            value = numberGen.run();
        } else if (!this.hasRange() && this.randomlyInitialized) {
            numberGen = new GenerateRandomInteger(32768);
            value = numberGen.run();
        } else {
            value = Integer.parseInt(prdProperty.getPRDValue().getInit());
        }

        setPropertyValue(value);
    }

    public DecimalProperty(PRDEnvProperty prdEnvProperty) throws RangeValueException,
            InvalidPropertyType, ValueOutOfRange, InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        setPropertyName(prdEnvProperty.getPRDName());
        this.propertyHistory = new LinkedHashMap<>();
        this.propertyType = prdEnvProperty.getType();
        Object randomInitialValue;
        Function numberGen;

        if (prdEnvProperty.getPRDRange() != null) {
            Range range = new DecimalRange(prdEnvProperty.getPRDRange());
            setPropertyRange(range);
            numberGen = new GenerateRandomInteger((int) prdEnvProperty.getPRDRange().getTo(),
                    (int) prdEnvProperty.getPRDRange().getFrom());
        } else {
            numberGen = new GenerateRandomInteger(32768);
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
            throws InvalidPropertyType, ValueOutOfRange, InvalidPropertyReference, IncompatibleTypes,
            InvalidEntity {
        if (object instanceof Integer) {
            if (hasRange()) {
                if ((Integer) object < (Integer) range.getMinValue() ||
                        (Integer) object > (Integer) range.getMaxValue()) {
                    throw new ValueOutOfRange(range.getMinValue(), range.getMaxValue());
                }
            }

            if (this.propertyValue == null || !this.propertyValue.equals(object)) {
                this.propertyValue = (Integer) object;
                this.resetTicksCounter();
            }

            if (this.propertyHistory != null) {
                Function getMax = new ExtractMaxKeyFromMap(this.propertyHistory);
                this.updateHistory((Integer) getMax.run());
            } else {
                this.updateHistory(0);
            }
        } else {
            throw new InvalidPropertyType(object.getClass().getSimpleName(), "Integer");
        }
    }

    @Override
    public Range getPropertyRange() {
        return range;
    }

    public void setPropertyRange(Range propertyRange) throws RangeValueException {
        this.range = new DecimalRange((Integer) propertyRange.getMinValue(), (Integer) propertyRange.getMaxValue());
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
        return range != null;
    }

    @Override
    public Property clone(Integer currentTick) {
        try {
            DecimalProperty clone = (DecimalProperty) super.clone();
            clone.propertyName = this.propertyName;
            clone.propertyType = this.propertyType;
            clone.randomlyInitialized = this.randomlyInitialized;
            clone.ticksSinceLastUpdate = 0;
            clone.propertyHistory = new LinkedHashMap<>();

            if (this.hasRange()) {
                clone.range = this.range.clone();
            }

            if (this.randomlyInitialized && this.propertyHistory.size() == 1) {
                Function generateRandomInt;
                if (clone.hasRange()) {
                    generateRandomInt = new GenerateRandomInteger((Integer) clone.range.getMaxValue(),
                            (Integer) clone.range.getMinValue());
                } else {
                    generateRandomInt = new GenerateRandomInteger(32768);
                }
                clone.propertyValue = (Integer) generateRandomInt.run();
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
            DecimalProperty clonedProperty = (DecimalProperty) super.clone();
            clonedProperty.propertyHistory = new LinkedHashMap<>(this.propertyHistory);
            return clonedProperty;
        } catch (CloneNotSupportedException  exception) {
            throw new AssertionError(exception);
        }
    }

    @Override
    public void randomise()
            throws InvalidPropertyType, ValueOutOfRange, InvalidPropertyReference, IncompatibleTypes,
            InvalidEntity {
        Function generateRandomDecimal;
        if (this.hasRange()) {
            generateRandomDecimal = new GenerateRandomInteger
                    ((Integer) this.range.getMaxValue(), (Integer) this.range.getMinValue());
        } else {
            generateRandomDecimal = new GenerateRandomInteger(32768);
        }
        this.setPropertyValue(generateRandomDecimal.run());
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
        Integer previousValue = propertyHistory.get(0), currentValue;

        for (Integer key : propertyHistory.keySet()) {
            currentValue = propertyHistory.get(key);

            if (Objects.equals(currentValue, previousValue)) {
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
        DecimalProperty that = (DecimalProperty) o;
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