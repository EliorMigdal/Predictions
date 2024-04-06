package engine.simulation.world.rule.action.condition.factory;

import engine.jaxb.generated.PRDCondition;
import engine.simulation.world.entity.Entity;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.rule.action.condition.Condition;
import engine.simulation.world.rule.action.condition.type.MultipleCondition;
import engine.simulation.world.rule.action.condition.type.SingularCondition;

import java.util.Collection;

public class ConditionGenerator {
    public Condition generateCondition(PRDCondition PRDcondition, Collection<Entity> entities,
                                       Environment environment) {
        if (PRDcondition.getSingularity().equalsIgnoreCase("single")) {
            return new SingularCondition(PRDcondition, environment);
        } else {
            return new MultipleCondition(PRDcondition, entities, environment);
        }
    }
}