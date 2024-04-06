package engine.simulation.world.rule.action.condition.logical.factory;
import engine.simulation.world.rule.action.condition.logical.LogicalOperator;
import engine.simulation.world.rule.action.condition.logical.factory.type.AndGenerator;
import engine.simulation.world.rule.action.condition.logical.factory.type.OrGenerator;

public class LogicalOperatorGenerator {
    public LogicalOperator generateLogicalOperator(String operator) {
        if (operator.equalsIgnoreCase("and")) {
            return new AndGenerator().generateLogicalOperator();
        } else {
            return new OrGenerator().generateLogicalOperator();
        }
    }
}