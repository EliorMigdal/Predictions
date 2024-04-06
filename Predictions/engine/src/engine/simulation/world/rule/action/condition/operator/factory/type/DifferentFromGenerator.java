package engine.simulation.world.rule.action.condition.operator.factory.type;
import engine.simulation.world.rule.action.condition.operator.Operator;
import engine.simulation.world.rule.action.condition.operator.factory.OperatorFactory;
import engine.simulation.world.rule.action.condition.operator.type.DifferentFrom;

public class DifferentFromGenerator implements OperatorFactory {
    @Override
    public Operator generateOperator() {
        return new DifferentFrom();
    }
}