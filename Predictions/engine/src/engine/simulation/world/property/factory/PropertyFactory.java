package engine.simulation.world.property.factory;

import engine.simulation.world.environment.exception.InvalidEnvironmentName;
import engine.simulation.world.property.Property;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.property.range.exception.RangeValueException;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

public interface PropertyFactory {
    Property generateProperty(Object property) throws InvalidEnvironmentName, InvalidPropertyType,
            RangeValueException, ValueOutOfRange, InvalidPropertyReference, IncompatibleTypes, InvalidEntity;
}