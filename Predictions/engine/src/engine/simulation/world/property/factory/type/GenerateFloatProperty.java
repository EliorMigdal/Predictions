package engine.simulation.world.property.factory.type;

import engine.jaxb.generated.PRDEnvProperty;
import engine.jaxb.generated.PRDProperty;
import engine.simulation.world.property.Property;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.property.factory.PropertyFactory;
import engine.simulation.world.property.range.exception.RangeValueException;
import engine.simulation.world.property.type.FloatProperty;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

public class GenerateFloatProperty implements PropertyFactory {
    @Override
    public Property generateProperty(Object property)
            throws InvalidPropertyType, RangeValueException, ValueOutOfRange,
            InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        if (property instanceof PRDProperty) {
            return new FloatProperty((PRDProperty) property);
        } else {
            return new FloatProperty((PRDEnvProperty) property);
        }
    }
}