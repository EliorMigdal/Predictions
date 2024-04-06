package engine.simulation.world.property;

import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.property.range.Range;
import engine.simulation.world.property.range.exception.RangeValueException;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

import java.io.Serializable;
import java.util.Map;

public interface Property extends Cloneable, Serializable {
    String getPropertyName();
    void setPropertyName(String propertyName);
    Object getPropertyValue();
    void setPropertyValue(Object object)
            throws InvalidPropertyType, ValueOutOfRange, InvalidPropertyReference, IncompatibleTypes, InvalidEntity;
    Range getPropertyRange();
    void setPropertyRange(Range propertyRange)
            throws RangeValueException;
    Boolean getRandomlyInitialized();
    void setRandomlyInitialized(Boolean randomlyInitialized);
    Boolean hasRange();
    Property clone(Integer currentTick)
            throws CloneNotSupportedException;
    Property clone()
            throws CloneNotSupportedException;
    void randomise()
            throws InvalidPropertyType, ValueOutOfRange, InvalidPropertyReference, IncompatibleTypes, InvalidEntity;
    void increaseTicksCount();
    void resetTicksCounter();
    Integer ticksSinceLastUpdate();
    void updateHistory(Integer currentTick);
    String getPropertyFirstTickValue();
    Double calculateConsistency();
    Double calculateAverage();
    boolean equals(Object o);
    Map<?, ?> getPropertyHistory();
}