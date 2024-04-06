package engine.simulation.world.rule.action.condition.logical;

import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.rule.action.condition.Condition;
import engine.simulation.world.rule.action.condition.exception.ConditionError;

import java.io.Serializable;
import java.util.Collection;

public interface LogicalOperator extends Serializable {
    boolean evaluateOperation(Collection<Condition> conditions, Instance instance)
            throws ConditionError;

    boolean evaluateOperation(Collection<Condition> conditions, Instance mainInstance, Instance secondaryInstance)
            throws ConditionError;

    String getOperatorName();
}