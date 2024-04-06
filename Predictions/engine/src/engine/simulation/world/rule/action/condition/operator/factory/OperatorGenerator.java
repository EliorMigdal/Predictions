package engine.simulation.world.rule.action.condition.operator.factory;
import engine.simulation.world.rule.action.condition.operator.Operator;
import engine.simulation.world.rule.action.condition.operator.factory.type.BiggerThanGenerator;
import engine.simulation.world.rule.action.condition.operator.factory.type.DifferentFromGenerator;
import engine.simulation.world.rule.action.condition.operator.factory.type.EqualToGenerator;
import engine.simulation.world.rule.action.condition.operator.factory.type.LowerThanGenerator;

public class OperatorGenerator {
    public Operator generateOperator(String operator) {
        if (operator.equalsIgnoreCase("bt")) {
            return new BiggerThanGenerator().generateOperator();
        } else if (operator.equalsIgnoreCase("lt")) {
            return new LowerThanGenerator().generateOperator();
        } else if (operator.equalsIgnoreCase("=")) {
            return new EqualToGenerator().generateOperator();
        } else {
            return new DifferentFromGenerator().generateOperator();
        }
    }
}