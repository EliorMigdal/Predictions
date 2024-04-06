package engine.simulation.world.environment.type;

import engine.jaxb.generated.PRDEnvProperty;
import engine.jaxb.generated.PRDEnvironment;
import engine.simulation.exception.creation.EnvironmentCreationException;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.environment.exception.InvalidEnvironmentName;
import engine.simulation.world.property.Property;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.property.factory.PropertyGenerator;
import engine.simulation.world.property.range.exception.RangeValueException;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EnvironmentVariable implements Environment {
    private final ArrayList<Property> properties;
    private final Map<String, Boolean> initializedMap;

    public EnvironmentVariable(PRDEnvironment prdEnvironment)
            throws EnvironmentCreationException {
        this.properties = new ArrayList<>();
        this.initializedMap = new HashMap<>();
        PropertyGenerator propertyGenerator = new PropertyGenerator();

        for (PRDEnvProperty property : prdEnvironment.getPRDEnvProperty()) {
            try {
                Property newProperty = propertyGenerator.generateProperty(property.getType(), property);
                properties.add(newProperty);
                initializedMap.put(newProperty.getPropertyName(), false);
            } catch (InvalidPropertyType | RangeValueException | InvalidPropertyReference | IncompatibleTypes |
                     ValueOutOfRange | InvalidEntity exception) {
                throw new EnvironmentCreationException(property.getPRDName(), exception.getMessage());
            }
        }
    }


    @Override
    public ArrayList<Property> getProperties() {
        return this.properties;
    }

    @Override
    public Property searchForProperty(String propertyName) {
        return properties.stream()
                .filter(property -> property.getPropertyName().equals(propertyName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Object getPropertyValue(String propertyName) {
        Optional<Property> desiredProperty = Optional.ofNullable(searchForProperty(propertyName));
        return desiredProperty.map(Property::getPropertyValue).orElse(null);
    }

    @Override
    public void setPropertyValue(String propertyName, Object propertyValue)
            throws InvalidEnvironmentName, InvalidPropertyType, ValueOutOfRange,
            InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        Property desiredProperty = searchForProperty(propertyName);

        if (desiredProperty == null) {
            throw new InvalidEnvironmentName(propertyName);
        } else {
            desiredProperty.setPropertyValue(propertyValue);

            initializedMap.put(propertyName, true);

        }
    }

    @Override
    public void randomiseRest()
            throws InvalidPropertyType, ValueOutOfRange, InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        for (Property environment : this.properties) {
            if (!initializedMap.get(environment.getPropertyName())) {
                environment.randomise();
            }
        }
    }

    @Override
    public Map<String, Boolean> getInitializedMap() {
        return initializedMap;
    }

    @Override
    public void resetInitMap() {
        initializedMap.keySet().forEach(Prop-> initializedMap.put(Prop, false));
    }
}