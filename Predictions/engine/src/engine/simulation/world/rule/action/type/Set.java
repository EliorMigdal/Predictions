package engine.simulation.world.rule.action.type;

import engine.jaxb.generated.PRDAction;
import engine.simulation.world.entity.Entity;
import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.entity.type.SecondaryEntity;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.property.Property;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.rule.action.Action;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.exception.ActionException;
import engine.simulation.world.rule.action.expression.Expression;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.simulation.world.rule.action.expression.type.ValueExpression;
import engine.utility.function.exception.InvalidPropertyReference;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Set implements Action {
    private Entity mainEntity;
    private Entity secondaryEntity;
    private final String propertyName;
    private final Expression expression;
    private final String type;

    public Set(PRDAction prdAction, Collection<Entity> entities, Environment environment) {
        this.type = prdAction.getType();
        this.propertyName = prdAction.getProperty();
        this.expression = new ValueExpression(prdAction.getValue(), environment);

        for (Entity entity : entities) {
            if (entity.getEntityName().equals(prdAction.getEntity())) {
                this.mainEntity = entity;
            } else if (prdAction.getPRDSecondaryEntity() != null && entity.getEntityName()
                    .equals(prdAction.getPRDSecondaryEntity().getEntity())) {
                this.secondaryEntity = new SecondaryEntity(prdAction.getPRDSecondaryEntity(), entities, environment);
            }
        }
    }

    public Entity getRelatedMainEntity() {
        return this.mainEntity;
    }

    @Override
    public Entity getRelatedSubEntity() {
        return this.secondaryEntity;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public Expression getExpression() {
        return this.expression;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getOperator() {
        return "set";
    }

    @Override
    public Map<String, String> getArgumentsMap() {
        Map<String,String> argumentsMap = new LinkedHashMap<>();
        argumentsMap.put("Property:", propertyName);
        argumentsMap.put("To:", expression.getExpression());
        return argumentsMap;
    }

    @Override
    public void executeAction(Instance mainInstance)
            throws ActionException {
        try {
            Property targetProperty = mainInstance.searchForProperty(this.propertyName);
            Object targetValue = this.expression.evaluate(mainInstance);

            targetProperty.setPropertyValue(targetValue);
        } catch (InvalidPropertyReference | IncompatibleTypes | InvalidPropertyType | InvalidEntity exception) {
            throw new ActionException(this.type, exception.getMessage());
        } catch (ValueOutOfRange ignored) {

        }
    }

    @Override
    public void executeAction(Instance mainInstance, Instance secondaryInstance)
            throws ActionException {
        try {
            Property targetProperty = mainInstance.searchForProperty(this.propertyName);

            if (targetProperty == null) {
                targetProperty = secondaryInstance.searchForProperty(this.propertyName);
            }

            Object targetValue = null;

            try {
                targetValue = this.expression.evaluate(mainInstance);
            } catch (InvalidEntity | InvalidPropertyReference | IncompatibleTypes ignored) {
            }

            if (targetValue == null) {
                targetValue = this.expression.evaluate(secondaryInstance);
            }

            targetProperty.setPropertyValue(targetValue);

        } catch (InvalidPropertyReference | IncompatibleTypes | InvalidPropertyType | InvalidEntity exception) {
            throw new ActionException(this.type, exception.getMessage());
        } catch (ValueOutOfRange ignored) {

        }
    }
}