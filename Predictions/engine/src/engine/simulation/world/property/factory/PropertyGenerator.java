package engine.simulation.world.property.factory;

import engine.simulation.world.property.Property;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.property.factory.type.GenerateBooleanProperty;
import engine.simulation.world.property.factory.type.GenerateDecimalProperty;
import engine.simulation.world.property.factory.type.GenerateFloatProperty;
import engine.simulation.world.property.factory.type.GenerateStringProperty;
import engine.simulation.world.property.range.exception.RangeValueException;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

public class PropertyGenerator {
    public Property generateProperty(String type, Object property)
            throws InvalidPropertyType, RangeValueException, ValueOutOfRange, InvalidPropertyReference,
            IncompatibleTypes, InvalidEntity {
        if (type.equalsIgnoreCase("float")) {
            return new GenerateFloatProperty().generateProperty(property);
        } else if (type.equalsIgnoreCase("decimal")) {
            return new GenerateDecimalProperty().generateProperty(property);
        } else if (type.equalsIgnoreCase("boolean")) {
            return new GenerateBooleanProperty().generateProperty(property);
        } else if (type.equalsIgnoreCase("string")) {
            return new GenerateStringProperty().generateProperty(property);
        } else {
            throw new InvalidPropertyType(type, "float/decimal/boolean/string");
        }
    }
}