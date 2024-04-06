package engine.simulation.world.rule.action.expression.handler.type;

import engine.simulation.world.property.Property;
import engine.simulation.world.rule.action.expression.handler.ExpressionHandler;

import java.util.Collection;

public class PropertyHandler implements ExpressionHandler {
    private final Collection<Property> entityProperties;

    public PropertyHandler(Collection<Property> entityProperties) {
        this.entityProperties = entityProperties;
    }

    @Override
    public Object handleExpression(String expression) {
        for (Property property : entityProperties) {
            if (property.getPropertyName().equals(expression)) {
                return property.getPropertyValue();
            }
        }

        return null;
    }
}
