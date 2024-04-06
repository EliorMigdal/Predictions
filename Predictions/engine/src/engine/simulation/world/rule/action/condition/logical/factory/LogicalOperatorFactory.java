package engine.simulation.world.rule.action.condition.logical.factory;
import engine.simulation.world.rule.action.condition.logical.LogicalOperator;

import java.io.Serializable;

public interface LogicalOperatorFactory  extends Serializable {
    LogicalOperator generateLogicalOperator();
}