package engine.simulation.world.environment;

import engine.simulation.world.environment.exception.InvalidEnvironmentName;
import engine.simulation.world.property.Property;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public interface Environment extends Serializable, Cloneable {
    ArrayList<Property> getProperties();
    Property searchForProperty(String propertyName);
    Object getPropertyValue(String propertyName);
    void setPropertyValue(String propertyName, Object propertyValue)
            throws InvalidEnvironmentName, InvalidPropertyType, ValueOutOfRange,
            InvalidPropertyReference, IncompatibleTypes, InvalidEntity;
    void randomiseRest()
            throws InvalidPropertyType, ValueOutOfRange, InvalidPropertyReference, IncompatibleTypes, InvalidEntity;
    Map<String, Boolean> getInitializedMap();
     void resetInitMap();
}