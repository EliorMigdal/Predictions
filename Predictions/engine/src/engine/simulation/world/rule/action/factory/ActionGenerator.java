package engine.simulation.world.rule.action.factory;

import engine.jaxb.generated.PRDAction;
import engine.simulation.world.entity.Entity;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.rule.action.Action;
import engine.simulation.world.rule.action.type.*;

import java.util.Collection;

public class ActionGenerator {
    public Action generateAction(PRDAction prdAction, Collection<Entity> entities, Environment environment) {
        if (prdAction.getType().equalsIgnoreCase("increase")) {
            return new Increase(prdAction, entities, environment);
        } else if (prdAction.getType().equalsIgnoreCase("decrease")) {
            return new Decrease(prdAction, entities, environment);
        } else if (prdAction.getType().equalsIgnoreCase("calculation")) {
            return new Calculation(prdAction, entities, environment);
        } else if (prdAction.getType().equalsIgnoreCase("condition")) {
            return new ConditionalAction(prdAction, entities, environment);
        } else if (prdAction.getType().equalsIgnoreCase("set")) {
            return new Set(prdAction, entities, environment);
        } else if (prdAction.getType().equalsIgnoreCase("kill")) {
            return new Kill(prdAction, entities, environment);
        } else if (prdAction.getType().equalsIgnoreCase("replace")) {
            return new Replace(prdAction, entities);
        } else if (prdAction.getType().equalsIgnoreCase("proximity")) {
            return new Proximity(prdAction, entities, environment);
        } else {
            return null;
        }
    }
}