package engine.simulation.world.rule.action.condition.type;

import engine.jaxb.generated.PRDCondition;
import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.rule.action.condition.Condition;
import engine.simulation.world.rule.action.condition.exception.ConditionError;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.condition.operator.Operator;
import engine.simulation.world.rule.action.condition.operator.factory.OperatorGenerator;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.simulation.world.rule.action.expression.type.ValueExpression;
import engine.utility.function.exception.InvalidPropertyReference;

public class SingularCondition implements Condition {
    private final ValueExpression property;
    private final ValueExpression condition;
    private final Operator operator;

    public SingularCondition(PRDCondition condition, Environment environment) {
        OperatorGenerator operatorGenerator = new OperatorGenerator();
        this.operator = operatorGenerator.generateOperator(condition.getOperator());
        this.property = new ValueExpression(condition.getProperty(), environment);
        this.condition = new ValueExpression(condition.getValue(), environment);
    }

    @Override
    public String getOperatorName() {
        return this.operator.getOperatorSign();
    }

    @Override
    public Integer getNumOfConditions() {
        return 1;
    }

    @Override
    public String describeCondition() {
        return property.getExpression() + operator.getOperatorSign() + condition.getExpression();
    }

    @Override
    public boolean isConditionMet(Instance mainInstance)
            throws ConditionError {
        try {
            Object propertyValue = this.property.evaluate(mainInstance);
            Object conditionValue = this.condition.evaluate(mainInstance);

            return operator.evaluateOperation(propertyValue, conditionValue);
        } catch (IncompatibleTypes | InvalidPropertyReference | InvalidEntity exception) {
            throw new ConditionError(this.condition.getExpression(), this.property.toString(),
                    this.operator.getOperatorSign(), exception.getMessage());
        }
    }

    @Override
    public boolean isConditionMet(Instance mainInstance, Instance secondaryInstance)
            throws ConditionError {
        try {
            Object propertyValue = null, conditionValue = null;
            try {
                propertyValue = this.property.evaluate(mainInstance);
            } catch (InvalidEntity | InvalidPropertyReference | IncompatibleTypes ignored) {
            }

            if (propertyValue == null) {
                propertyValue = this.property.evaluate(secondaryInstance);
            }

            try {
                conditionValue = this.condition.evaluate(secondaryInstance);
            } catch (InvalidEntity | InvalidPropertyReference | IncompatibleTypes ignored) {
            }

            if (conditionValue == null) {
                conditionValue = this.property.evaluate(secondaryInstance);
            }

            return operator.evaluateOperation(propertyValue, conditionValue);
        } catch (IncompatibleTypes | InvalidPropertyReference | InvalidEntity exception) {
            throw new ConditionError(this.condition.getExpression(), this.property.toString(),
                    this.operator.getOperatorSign(), exception.getMessage());
        }
    }
}