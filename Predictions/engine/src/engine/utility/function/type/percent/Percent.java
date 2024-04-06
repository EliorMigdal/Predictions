package engine.utility.function.type.percent;

import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;

public class Percent implements Function {
    private final String arguments;
    private final Environment environmentVariables;
    private final Instance mainInstance;

    public Percent(String arguments, Environment environmentVariables, Instance mainInstance) {
        this.arguments = arguments;
        this.environmentVariables = environmentVariables;
        this.mainInstance = mainInstance;
    }

    @Override
    public String getName() {
        return "percent";
    }

    @Override
    public Object run()
            throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        Object firstExpressionValue = new EvaluateFirstExpression(arguments, environmentVariables,
                mainInstance).run();
        Object secondExpressionValue = new EvaluateSecondExpression(arguments, environmentVariables,
                mainInstance).run();

        if (firstExpressionValue instanceof Integer && secondExpressionValue instanceof Integer) {
            return (Integer) firstExpressionValue * ((Integer) secondExpressionValue / 100);
        } else if (firstExpressionValue instanceof Integer && secondExpressionValue instanceof Float) {
            return (Integer) firstExpressionValue * (Float) secondExpressionValue / 100;
        } else if (firstExpressionValue instanceof Float && secondExpressionValue instanceof Integer) {
            return (Float) firstExpressionValue * (Integer) secondExpressionValue / 100;
        } else if (firstExpressionValue instanceof Float && secondExpressionValue instanceof Float) {
            return (Float) firstExpressionValue * (Float) secondExpressionValue / 100;
        } else {
            throw new IncompatibleTypes(firstExpressionValue.getClass().getSimpleName(),
                    secondExpressionValue.getClass().getSimpleName());
        }
    }
}