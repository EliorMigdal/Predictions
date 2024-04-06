package engine.simulation.world.rule.action.condition;

import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.rule.action.condition.exception.ConditionError;

import java.io.Serializable;

public interface Condition  extends Serializable {
    boolean isConditionMet(Instance instance)
            throws ConditionError;
    boolean isConditionMet(Instance mainInstance, Instance secondaryInstance)
            throws ConditionError;
    String getOperatorName();
    Integer getNumOfConditions();
    String describeCondition();
}