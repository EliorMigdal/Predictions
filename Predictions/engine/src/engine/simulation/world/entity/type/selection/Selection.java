package engine.simulation.world.entity.type.selection;

import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.rule.action.condition.Condition;
import engine.simulation.world.rule.action.condition.exception.ConditionError;

public class Selection {
    private final String count;
    private Condition condition;

    public Selection(String count) {
        this.count = count;
    }

    public Selection(String count, Condition condition) {
        this.count = count;
        this.condition = condition;
    }

    public String getCount() {
        return this.count;
    }

    public boolean isConditionMet(Instance instance)
            throws ConditionError {
        return condition.isConditionMet(instance);
    }
}