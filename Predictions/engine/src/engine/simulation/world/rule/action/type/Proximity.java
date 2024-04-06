package engine.simulation.world.rule.action.type;

import engine.jaxb.generated.PRDAction;
import engine.simulation.world.entity.Entity;
import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.rule.action.Action;
import engine.simulation.world.rule.action.condition.exception.ConditionError;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.exception.ActionException;
import engine.simulation.world.rule.action.expression.Expression;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.simulation.world.rule.action.expression.type.ValueExpression;
import engine.simulation.world.rule.action.factory.ActionGenerator;
import engine.utility.function.exception.InvalidPropertyReference;
import engine.utility.function.predicate.Predicate;
import engine.utility.function.predicate.type.proximity.CheckProximity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Proximity implements Action {
    private Entity sourceEntity;
    private Entity targetEntity;
    private final Collection<Action> actions;
    private final Expression distance;
    private final String type;

    public Proximity(PRDAction prdAction, Collection<Entity> entities, Environment environment) {
        this.type = prdAction.getType();
        this.distance = new ValueExpression(prdAction.getPRDEnvDepth().getOf(), environment);
        this.actions = new ArrayList<>();

        for (Entity entity : entities) {
            if (entity.getEntityName().equals(prdAction.getPRDBetween().getSourceEntity())) {
                this.sourceEntity = entity;
            } else if (entity.getEntityName().equals(prdAction.getPRDBetween().getTargetEntity())) {
                this.targetEntity = entity;
            }
        }

        ActionGenerator actionGenerator = new ActionGenerator();
        for (PRDAction action : prdAction.getPRDActions().getPRDAction()) {
            Action newAction = actionGenerator.generateAction(action, entities, environment);
            this.actions.add(newAction);
        }
    }

    @Override
    public void executeAction(Instance mainInstance)
            throws ConditionError, ActionException {
        try {
            for (Instance secondaryInstance : this.targetEntity.getEntityInstances()) {
                if (secondaryInstance.isAlive()) {
                    Object numericDistance = this.distance.evaluate(mainInstance);
                    Predicate checkClosure = null;

                    if (numericDistance instanceof Integer) {
                        checkClosure = new CheckProximity(mainInstance.getInstanceLocation(),
                                secondaryInstance.getInstanceLocation(), (Integer) numericDistance);
                    } else if (numericDistance instanceof Float) {
                        checkClosure = new CheckProximity(mainInstance.getInstanceLocation(),
                                secondaryInstance.getInstanceLocation(), (Float) numericDistance);
                    }
                    if (checkClosure != null && checkClosure.run()) {
                        for (Action action : this.actions) {
                            action.executeAction(mainInstance, secondaryInstance);
                        }

                        break;
                    }
                }
            }
        } catch (InvalidPropertyReference | IncompatibleTypes | InvalidEntity exception) {
            throw new ActionException(this.type, exception.getMessage());
        }
    }

    @Override
    public void executeAction(Instance mainInstance, Instance secondaryInstance)
            throws ConditionError, ActionException {
        try {
            Object numericDistance = this.distance.evaluate(mainInstance);
            Predicate checkClosure = null;

            if (numericDistance instanceof Integer) {
                checkClosure = new CheckProximity(mainInstance.getInstanceLocation(),
                        secondaryInstance.getInstanceLocation(), (Integer) numericDistance);
            } else if (numericDistance instanceof Float) {
                checkClosure = new CheckProximity(mainInstance.getInstanceLocation(),
                        secondaryInstance.getInstanceLocation(), (Float) numericDistance);
            }
            if (checkClosure != null && checkClosure.run()) {
                for (Action action : this.actions) {
                    action.executeAction(mainInstance, secondaryInstance);
                }
            }
        } catch (InvalidEntity | InvalidPropertyReference | IncompatibleTypes exception) {
            throw new ActionException(this.type, exception.getMessage());
        }
    }

    @Override
    public Entity getRelatedMainEntity() {
        return this.sourceEntity;
    }

    @Override
    public Entity getRelatedSubEntity() {
        return null;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getOperator() {
        return null;
    }

    @Override
    public Map<String, String> getArgumentsMap() {
        Map<String,String> argumentsMap = new LinkedHashMap<>();
        argumentsMap.put("Circular distance:", this.distance.getExpression());
        argumentsMap.put("Num of actions:", ((Integer) this.actions.size()).toString());
        return argumentsMap;
    }
}