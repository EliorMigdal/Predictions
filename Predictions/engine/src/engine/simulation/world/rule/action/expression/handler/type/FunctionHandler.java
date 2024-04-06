package engine.simulation.world.rule.action.expression.handler.type;

import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.simulation.world.rule.action.expression.handler.ExpressionHandler;
import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;
import engine.utility.function.type.ExtractFunctionParameter;
import engine.utility.function.type.environment.GetEnvironmentValue;
import engine.utility.function.type.evaluate.Evaluate;
import engine.utility.function.type.percent.Percent;
import engine.utility.function.type.random.GenerateRandomFloat;
import engine.utility.function.type.ticks.Ticks;

public class FunctionHandler implements ExpressionHandler {
    private final String functionName;
    private final Environment environmentVariables;
    private final Instance mainInstance;

    public FunctionHandler(String functionName, Environment environmentVariables, Instance mainInstance) {
        this.functionName = functionName;
        this.environmentVariables = environmentVariables;
        this.mainInstance = mainInstance;
    }

    @Override
    public Object handleExpression(String expression)
            throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        Function extractFunctionArgument = new ExtractFunctionParameter(expression);
        Object functionArgument = extractFunctionArgument.run();
        Function desiredFunction;

        if (functionName.equals("random") && functionArgument instanceof String) {
            desiredFunction = new GenerateRandomFloat((String) functionArgument);
        } else if (functionName.equals("environment") && functionArgument instanceof String) {
            desiredFunction = new GetEnvironmentValue((String) functionArgument, environmentVariables);
        } else if (functionName.equals("evaluate")) {
            desiredFunction = new Evaluate((String) functionArgument, mainInstance);
        } else if (functionName.equals("percent")) {
            desiredFunction = new Percent(expression, environmentVariables, mainInstance);
        } else if (functionName.equals("ticks")) {
            desiredFunction = new Ticks((String) functionArgument, mainInstance);
        } else {
            return null;
        }

        return desiredFunction.run();
    }
}
