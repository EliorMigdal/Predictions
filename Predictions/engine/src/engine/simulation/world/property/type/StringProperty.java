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
import engine.utility.function.type.random.GenerateRandomString;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class StringProperty implements Property, Cloneable {
    private String propertyName;
    private String propertyValue;
    private String propertyType;
    private Boolean randomlyInitialized;
    private Integer ticksSinceLastUpdate = 0;
    private LinkedHashMap<Integer, String> propertyHistory;

    public StringProperty(PRDProperty prdProperty)
            throws InvalidPropertyType, InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        setPropertyName(prdProperty.getPRDName());
        setRandomlyInitialized(prdProperty.getPRDValue().isRandomInitialize());
        this.propertyHistory = new LinkedHashMap<>();
        this.propertyType = prdProperty.getType();
        Object value;

        if (this.randomlyInitialized) {
            Function stringGen = new GenerateRandomString();
            value = stringGen.run();
            setPropertyValue(value);
        } else {
            setPropertyValue(prdProperty.getPRDValue().getInit());
        }
    }

    public StringProperty(PRDEnvProperty prdEnvProperty)
            throws InvalidPropertyType, InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        setPropertyName(prdEnvProperty.getPRDName());
        this.propertyHistory = new LinkedHashMap<>();
        this.propertyType = prdEnvProperty.getType();
        Function stringGen = new GenerateRandomString();
        setPropertyValue(stringGen.run());
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
        Object oldValue = this.propertyValue;
        if (object instanceof String) {
            this.propertyValue = (String)object;
        } else if (object instanceof Boolean) {
            if ((Boolean)object) {
                this.propertyValue = "true";
            } else {
                this.propertyValue = "false";
            }
        } else {
            throw new InvalidPropertyType(object.getClass().getSimpleName(), "String");
        }

        if (!this.propertyValue.equals(oldValue)) {
            this.resetTicksCounter();
        }

        if (this.propertyHistory != null) {
            Function getMax = new ExtractMaxKeyFromMap(this.propertyHistory);
            this.updateHistory((Integer) getMax.run());
        } else {
            this.updateHistory(0);
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
            StringProperty clone = (StringProperty) super.clone();
            clone.propertyName = this.propertyName;
            clone.propertyType = this.propertyType;
            clone.randomlyInitialized = this.randomlyInitialized;
            clone.ticksSinceLastUpdate = 0;
            clone.propertyHistory = new LinkedHashMap<>();
            if (this.randomlyInitialized && this.propertyHistory.size() == 1) {
                Function generateRandomString = new GenerateRandomString();
                clone.propertyValue = (String) generateRandomString.run();
            } else {
                clone.propertyValue = this.propertyValue;
                clone.propertyHistory.put(currentTick, this.propertyValue);
            }
            return clone;
        } catch (CloneNotSupportedException | InvalidPropertyReference | IncompatibleTypes | InvalidEntity error) {
            throw new AssertionError(error);
        }
    }

    @Override
    public Property clone() {
        try {
            StringProperty clonedProperty = (StringProperty) super.clone();
            clonedProperty.propertyHistory = new LinkedHashMap<>(this.propertyHistory);
            return clonedProperty;
        } catch (CloneNotSupportedException  exception) {
            throw new AssertionError(exception);
        }
    }

    @Override
    public void randomise()
            throws InvalidPropertyType, InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        this.setPropertyValue(new GenerateRandomString().run());
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
        return this.propertyHistory.get(0);
    }

    @Override
    public Double calculateConsistency() {
        int consistencyValue = 0, numOfChanges = 0;
        String previousValue = propertyHistory.get(0), currentValue;

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
        return -1D;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringProperty that = (StringProperty) o;
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