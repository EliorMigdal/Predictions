package engine.simulation.world.rule.action.condition.type;

import engine.jaxb.generated.PRDCondition;
import engine.simulation.world.entity.Entity;
import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.rule.action.condition.Condition;
import engine.simulation.world.rule.action.condition.exception.ConditionError;
import engine.simulation.world.rule.action.condition.factory.ConditionGenerator;
import engine.simulation.world.rule.action.condition.logical.LogicalOperator;
import engine.simulation.world.rule.action.condition.logical.factory.LogicalOperatorGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class MultipleCondition implements Condition {
    private final Collection<Condition> conditions;
    private final LogicalOperator logicalOperator;

    public MultipleCondition(PRDCondition condition, Collection<Entity> entities, Environment environment) {
        LogicalOperatorGenerator logicalOpGen = new LogicalOperatorGenerator();
        ConditionGenerator conditionGenerator;

        this.logicalOperator = logicalOpGen.generateLogicalOperator(condition.getLogical());
        this.conditions = new ArrayList<>();

        for (PRDCondition currCondition : condition.getPRDCondition()) {
            conditionGenerator = new ConditionGenerator();
            Optional<Condition> newCondition = Optional.ofNullable(conditionGenerator
                    .generateCondition(currCondition, entities, environment));
            newCondition.ifPresent(this::addCondition);
        }
    }

    public void addCondition(Condition condition) {
        conditions.add(condition);
    }

    @Override
    public String getOperatorName() {
        return this.logicalOperator.getOperatorName();
    }

    @Override
    public Integer getNumOfConditions() {
        return conditions.size();
    }

    @Override
    public String describeCondition() {
        return null;
    }

    @Override
    public boolean isConditionMet(Instance mainInstance)
            throws ConditionError {
        return logicalOperator.evaluateOperation(conditions, mainInstance);
    }

    @Override
    public boolean isConditionMet(Instance mainInstance, Instance secondaryInstance)
            throws ConditionError {
        return logicalOperator.evaluateOperation(conditions, mainInstance, secondaryInstance);
    }
}