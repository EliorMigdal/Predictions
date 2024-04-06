package engine.utility.function.type.percent;

import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.simulation.world.rule.action.expression.type.ValueExpression;
import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;

public class EvaluateSecondExpression implements Function {
    private final String argument;
    private final Environment environmentVariables;
    private final Instance mainInstance;

    public EvaluateSecondExpression(String argument, Environment environmentVariables, Instance mainInstance) {
        this.argument = argument;
        this.environmentVariables = environmentVariables;
        this.mainInstance = mainInstance;
    }

    @Override
    public String getName() {
        return "extractSecondExpression";
    }

    @Override
    public Object run()
            throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        StringBuilder secondExpression = new StringBuilder();
        boolean foundComma = false;

        for (char character : argument.toCharArray()) {
            if (character != ',' && character != ')' && foundComma) {
                secondExpression.append(character);
            } else if (character == ',') {
                foundComma = true;
            } else if (character == ')' && foundComma) {
                secondExpression.append(character);
                break;
            }
        }

        return new ValueExpression(secondExpression.toString(), environmentVariables)
                .evaluate(mainInstance);
    }
}