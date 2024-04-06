package engine.simulation.world.rule.action.expression.type;

import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.Expression;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.simulation.world.rule.action.expression.handler.ExpressionHandler;
import engine.simulation.world.rule.action.expression.handler.type.CastHandler;
import engine.simulation.world.rule.action.expression.handler.type.FunctionHandler;
import engine.simulation.world.rule.action.expression.handler.type.PropertyHandler;
import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;
import engine.utility.function.type.ExtractFunctionName;

public class ValueExpression implements Expression {
    private String expression;
    private final Environment environment;

    public ValueExpression(String expression, Environment environment) {
        this.expression = expression;
        this.environment = environment;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Object evaluate(Instance mainInstance)
            throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        Function extractFunctionName = new ExtractFunctionName(this.expression);
        Object functionName = extractFunctionName.run();
        ExpressionHandler expressionHandler;

        if (functionName instanceof String) {
            expressionHandler = new FunctionHandler((String) functionName, environment, mainInstance);
            return expressionHandler.handleExpression(this.expression);
        } else {
            expressionHandler = new PropertyHandler(mainInstance.getProperties());
            Object optionalProperty = expressionHandler.handleExpression(this.expression);

            if (optionalProperty != null) {
                return optionalProperty;
            } else {
                return new CastHandler().handleExpression(this.expression);
            }
        }
    }
}