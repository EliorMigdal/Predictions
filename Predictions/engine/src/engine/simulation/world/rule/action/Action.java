package engine.simulation.world.rule.action;

import engine.simulation.world.entity.Entity;
import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.rule.action.condition.exception.ConditionError;
import engine.simulation.world.rule.action.exception.ActionException;

import java.io.Serializable;
import java.util.Map;

public interface Action extends Serializable {
    void executeAction(Instance mainInstance)
            throws ConditionError, ActionException;
    void executeAction(Instance mainInstance, Instance secondaryInstance)
            throws ConditionError, ActionException;
    Entity getRelatedMainEntity();
    Entity getRelatedSubEntity();
    String getType();
    String getOperator();
    Map<String, String> getArgumentsMap();
}