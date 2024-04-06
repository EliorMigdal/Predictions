package engine.simulation.world.rule.action.condition.logical.type;

import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.rule.action.condition.Condition;
import engine.simulation.world.rule.action.condition.exception.ConditionError;
import engine.simulation.world.rule.action.condition.logical.LogicalOperator;

import java.util.Collection;

public class OrOperator implements LogicalOperator {
    public OrOperator() {}

    @Override
    public boolean evaluateOperation(Collection<Condition> conditions, Instance instance)
            throws ConditionError {
        boolean output = false;

        for (Condition condition : conditions) {
            if (condition.isConditionMet(instance)) {
                output = true;
                break;
            }
        }

        return output;
    }

    @Override
    public boolean evaluateOperation(Collection<Condition> conditions,
                                     Instance mainInstance, Instance secondaryInstance)
            throws ConditionError {
        boolean output = false;

        for (Condition condition : conditions) {
            if (condition.isConditionMet(mainInstance, secondaryInstance)) {
                output = true;
                break;
            }
        }

        return output;
    }

    @Override
    public String getOperatorName() {
        return "Or";
    }
}