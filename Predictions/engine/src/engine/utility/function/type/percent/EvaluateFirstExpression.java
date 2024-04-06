package engine.utility.function.type.percent;

import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.simulation.world.rule.action.expression.type.ValueExpression;
import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;

public class EvaluateFirstExpression implements Function {
    private final String argument;
    private final Environment environmentVariables;
    private final Instance mainInstance;

    public EvaluateFirstExpression(String argument, Environment environmentVariables, Instance mainInstance) {
        this.argument = argument;
        this.environmentVariables = environmentVariables;
        this.mainInstance = mainInstance;
    }

    @Override
    public String getName() {
        return "extractFirstExpression";
    }

    @Override
    public Object run()
            throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        StringBuilder firstExpression = new StringBuilder();
        boolean foundOpeningBracket = false;

        for (char character : argument.toCharArray()) {
            if (character != ',' && foundOpeningBracket) {
                firstExpression.append(character);
            } else if (character == '(') {
                foundOpeningBracket = true;
            } else if (character == ',') {
                break;
            }
        }

        return new ValueExpression(firstExpression.toString(), environmentVariables)
                .evaluate(mainInstance);
    }
}
