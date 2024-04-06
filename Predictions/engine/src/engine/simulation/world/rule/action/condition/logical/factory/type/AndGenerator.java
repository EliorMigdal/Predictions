package engine.simulation.world.rule.action.condition.logical.factory.type;
import engine.simulation.world.rule.action.condition.logical.LogicalOperator;
import engine.simulation.world.rule.action.condition.logical.factory.LogicalOperatorFactory;
import engine.simulation.world.rule.action.condition.logical.type.AndOperator;

public class AndGenerator implements LogicalOperatorFactory {
    @Override
    public LogicalOperator generateLogicalOperator() {
        return new AndOperator();
    }
}