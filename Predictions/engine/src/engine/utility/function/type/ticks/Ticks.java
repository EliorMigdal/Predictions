package engine.utility.function.type.ticks;

import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.property.Property;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;
import engine.utility.function.type.evaluate.ExtractEntityName;
import engine.utility.function.type.evaluate.ExtractPropertyName;

public class Ticks implements Function {
    private final String argument;
    private final Instance mainInstance;

    public Ticks(String argument, Instance mainInstance) {
        this.argument = argument;
        this.mainInstance = mainInstance;
    }

    @Override
    public String getName() {
        return "ticks";
    }

    @Override
    public Object run()
            throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        Function extractEntityName = new ExtractEntityName(argument);
        Object entityName = extractEntityName.run();

        Function extractPropertyName = new ExtractPropertyName(argument);
        Object propertyName = extractPropertyName.run();

        if (entityName instanceof String && propertyName instanceof String) {
            if (!mainInstance.getEntityName().equals(entityName)) {
                throw new InvalidEntity(mainInstance.getEntityName(), (String) entityName);
            } else {
                for (Property property : mainInstance.getProperties()) {
                    if (property.getPropertyName().equals(propertyName)) {
                        return property.ticksSinceLastUpdate();
                    }
                }

                throw new InvalidPropertyReference((String) propertyName, (String) entityName);
            }
        } else {
            return null;
        }
    }
}
