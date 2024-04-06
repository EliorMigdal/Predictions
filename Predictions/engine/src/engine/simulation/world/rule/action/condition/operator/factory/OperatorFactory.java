package engine.simulation.world.rule.action.condition.operator.factory;
import engine.simulation.world.rule.action.condition.operator.Operator;

import java.io.Serializable;

public interface OperatorFactory  extends Serializable {
    Operator generateOperator();
}
