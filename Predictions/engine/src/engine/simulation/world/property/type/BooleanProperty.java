package engine.simulation.world.property.type;

import engine.jaxb.generated.PRDEnvProperty;
import engine.jaxb.generated.PRDProperty;
import engine.simulation.world.property.Property;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.range.Range;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;
import engine.utility.function.type.max.ExtractMaxKeyFromMap;
import engine.utility.function.type.random.GenerateRandomBoolean;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class BooleanProperty implements Property, Cloneable {
    private String propertyName;
    private Boolean propertyValue;
    private String propertyType;
    private Boolean randomlyInitialized;
    private Integer ticksSinceLastUpdate = 0;
    private LinkedHashMap<Integer, Boolean> propertyHistory;

    public BooleanProperty(PRDProperty property)
            throws InvalidPropertyType, InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        setPropertyName(property.getPRDName());
        setRandomlyInitialized(property.getPRDValue().isRandomInitialize());
        this.propertyHistory = new LinkedHashMap<>();
        this.propertyType = property.getType();

        if (this.randomlyInitialized) {
            Function boolGen = new GenerateRandomBoolean();
            setPropertyValue(boolGen.run());
        } else if (property.getPRDValue().getInit().equalsIgnoreCase("true") ||
                property.getPRDValue().getInit().equalsIgnoreCase("false")) {
            propertyValue = property.getPRDValue().getInit().equalsIgnoreCase("true");
            setPropertyValue(propertyValue);
        } else {
            throw new InvalidPropertyType(property.getType(), "Boolean");
        }
    }

    public BooleanProperty(PRDEnvProperty prdEnvProperty)
            throws InvalidPropertyType, InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        setPropertyName(prdEnvProperty.getPRDName());
        Function boolGen = new GenerateRandomBoolean();
        this.propertyHistory = new LinkedHashMap<>();
        setPropertyValue(boolGen.run());
        this.propertyType = prdEnvProperty.getType();
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
            throws InvalidPropertyType, InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        if (object instanceof Boolean) {
            if (this.propertyValue == null || !this.propertyValue.equals(object)) {
                this.propertyValue = (Boolean)object;
                this.resetTicksCounter();
            }

            if (this.propertyHistory != null) {
                Function getMax = new ExtractMaxKeyFromMap(this.propertyHistory);
                this.updateHistory((Integer) getMax.run());
            } else {
                this.updateHistory(0);
            }
        } else {
            throw new InvalidPropertyType(object.getClass().getSimpleName(), "Boolean");
        }
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
    public Range getPropertyRange() {
        return null;
    }

    @Override
    public void setPropertyRange(Range propertyRange) {

    }

    @Override
    public Boolean hasRange() {
        return false;
    }

    @Override
    public Property clone(Integer currentTick) {
        try {
            BooleanProperty clone = (BooleanProperty) super.clone();
            clone.propertyName = this.propertyName;
            clone.propertyType = this.propertyType;
            clone.randomlyInitialized = this.randomlyInitialized;
            clone.ticksSinceLastUpdate = 0;
            clone.propertyHistory = new LinkedHashMap<>();
            if (this.randomlyInitialized && this.propertyHistory.size() == 1) {
                Function generateRandomBool = new GenerateRandomBoolean();
                clone.propertyValue = (Boolean) generateRandomBool.run();
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
            BooleanProperty clonedProperty = (BooleanProperty) super.clone();
            clonedProperty.propertyHistory = new LinkedHashMap<>(this.propertyHistory);
            return clonedProperty;
        } catch (CloneNotSupportedException  exception) {
            throw new AssertionError(exception);
        }
    }

    @Override
    public void randomise()
            throws InvalidPropertyType, InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        Function generateRandomBool = new GenerateRandomBoolean();
        this.setPropertyValue(generateRandomBool.run());
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
        int consistencyCounter = 0, numOfChanges = 0;
        Boolean previousValue = propertyHistory.get(0), currentValue;

        for (Integer key : propertyHistory.keySet()) {
            currentValue = propertyHistory.get(key);

            if (currentValue == previousValue) {
                consistencyCounter++;
            } else {
                numOfChanges++;
            }

            previousValue = currentValue;
        }

        DecimalFormat format = new DecimalFormat("0.000");
        Double result;

        if (numOfChanges > 0) {
            result = (double) consistencyCounter / numOfChanges;
        } else {
            result = (double) consistencyCounter;
        }

        return Double.parseDouble(format.format(result));
    }

    @Override
    public Double calculateAverage() {
        return -1D;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleanProperty that = (BooleanProperty) o;
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