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
import engine.simulation.world.rule.action.expression.type.binary.Arithmetic;
import engine.simulation.world.rule.action.expression.type.binary.exception.DivideByZero;
import engine.simulation.world.rule.action.expression.type.binary.type.Divide;
import engine.simulation.world.rule.action.expression.type.binary.type.Multiply;
import engine.utility.function.exception.InvalidPropertyReference;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Calculation implements Action {
    private Entity mainEntity;
    private Entity secondaryEntity = null;
    private final String type;
    private final Arithmetic arithmeticOperation;
    private final String resultProp;
    private final Expression firstArgument;
    private final Expression secondArgument;

    public Calculation(PRDAction prdAction, Collection<Entity> entities, Environment environment) {
        this.type = prdAction.getType();
        this.resultProp = prdAction.getResultProp();

        if (prdAction.getPRDMultiply() != null) {
            this.firstArgument = new ValueExpression(prdAction.getPRDMultiply().getArg1(), environment);
            this.secondArgument = new ValueExpression(prdAction.getPRDMultiply().getArg2(), environment);
            this.arithmeticOperation = new Multiply();
        } else {
            this.firstArgument = new ValueExpression(prdAction.getPRDDivide().getArg1(), environment);
            this.secondArgument = new ValueExpression(prdAction.getPRDDivide().getArg2(), environment);
            arithmeticOperation = new Divide();
        }

        for (Entity entity : entities) {
            if (entity.getEntityName().equals(prdAction.getEntity())) {
                this.mainEntity = entity;
            } else if (prdAction.getPRDSecondaryEntity() != null && entity.getEntityName()
                    .equals(prdAction.getPRDSecondaryEntity().getEntity())) {
                this.secondaryEntity = new SecondaryEntity(prdAction.getPRDSecondaryEntity(), entities, environment);
            }
        }
    }

    @Override
    public Entity getRelatedMainEntity() {
        return this.mainEntity;
    }

    @Override
    public Entity getRelatedSubEntity() {
        return this.secondaryEntity;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String getOperator() {
        return this.arithmeticOperation.getOperationName();
    }

    @Override
    public Map<String, String> getArgumentsMap() {
        Map<String,String> arguments = new LinkedHashMap<>();

        arguments.put("Argument#1:", this.firstArgument.getExpression());
        arguments.put("Argument#2:", this.secondArgument.getExpression());

        arguments.put("Result prop:", resultProp);

        return arguments;
    }

    @Override
    public void executeAction(Instance mainInstance)
            throws ActionException {
        try {
            Property propertyValue = mainInstance.searchForProperty(resultProp);
            Object firstArgument = this.firstArgument.evaluate(mainInstance);
            Object secondArgument = this.secondArgument.evaluate(mainInstance);
            propertyValue.setPropertyValue(this.arithmeticOperation.calculate(firstArgument, secondArgument));
        } catch (DivideByZero | IncompatibleTypes | InvalidPropertyReference | InvalidPropertyType
                 | InvalidEntity exception) {
            throw new ActionException(this.type, exception.getMessage());
        } catch (ValueOutOfRange ignored) {

        }
    }

    @Override
    public void executeAction(Instance mainInstance, Instance secondaryInstance)
            throws ActionException {
        try {
            Property propertyValue = mainInstance.searchForProperty(resultProp);

            if (propertyValue == null) {
                propertyValue = secondaryInstance.searchForProperty(resultProp);
            }

            Object firstArgument = null, secondArgument = null;

            try {
                firstArgument = this.firstArgument.evaluate(mainInstance);
            } catch (InvalidEntity | InvalidPropertyReference | IncompatibleTypes ignored) {
            }

            if (firstArgument == null) {
                firstArgument = this.firstArgument.evaluate(secondaryInstance);
            }

            try {
                secondArgument = this.secondArgument.evaluate(mainInstance);
            } catch (InvalidEntity | InvalidPropertyReference | IncompatibleTypes ignored) {
            }

            if (secondArgument == null) {
                secondArgument = this.secondArgument.evaluate(secondaryInstance);
            }

            propertyValue.setPropertyValue(this.arithmeticOperation.calculate(firstArgument, secondArgument));
        } catch (InvalidEntity | InvalidPropertyReference | IncompatibleTypes | InvalidPropertyType |
                 DivideByZero exception) {
            throw new ActionException(mainInstance.getEntityName(), exception.getMessage());
        } catch (ValueOutOfRange ignored) {

        }
    }
}