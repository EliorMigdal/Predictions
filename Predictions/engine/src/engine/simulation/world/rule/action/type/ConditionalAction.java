package engine.simulation.world.rule.action.type;

import engine.jaxb.generated.PRDAction;
import engine.simulation.world.entity.Entity;
import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.entity.type.SecondaryEntity;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.rule.action.Action;
import engine.simulation.world.rule.action.condition.Condition;
import engine.simulation.world.rule.action.condition.exception.ConditionError;
import engine.simulation.world.rule.action.condition.factory.ConditionGenerator;
import engine.simulation.world.rule.action.condition.type.MultipleCondition;
import engine.simulation.world.rule.action.condition.type.SingularCondition;
import engine.simulation.world.rule.action.exception.ActionException;
import engine.simulation.world.rule.action.factory.ActionGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConditionalAction implements Action {
    private final Condition condition;
    private final ArrayList<Action> thenActions;
    private final ArrayList<Action> elseActions;
    private Entity mainEntity;
    private Entity secondaryEntity = null;
    private final String type;

    public ConditionalAction(PRDAction prdAction, Collection<Entity> entities, Environment environment) {
        this.type = prdAction.getType();

        for (Entity entity : entities) {
            if (entity.getEntityName().equals(prdAction.getEntity())) {
                this.mainEntity = entity;
            } else if (prdAction.getPRDSecondaryEntity() != null && entity.getEntityName()
                    .equals(prdAction.getPRDSecondaryEntity().getEntity())) {
                this.secondaryEntity = new SecondaryEntity(prdAction.getPRDSecondaryEntity(),
                        entities, environment);
            }
        }

        ConditionGenerator conditionGenerator = new ConditionGenerator();
        this.condition = conditionGenerator.generateCondition(prdAction.getPRDCondition(), entities, environment);
        this.thenActions = new ArrayList<>();
        this.elseActions = new ArrayList<>();
        ActionGenerator actionGenerator = new ActionGenerator();

        if (prdAction.getPRDThen() != null) {
            for (PRDAction action : prdAction.getPRDThen().getPRDAction()) {
                Action newAction = actionGenerator.generateAction(action, entities, environment);
                this.thenActions.add(newAction);
            }
        }

        if (prdAction.getPRDElse() != null) {
            for (PRDAction action : prdAction.getPRDElse().getPRDAction()) {
                Action newAction = actionGenerator.generateAction(action, entities, environment);
                this.elseActions.add(newAction);
            }
        }
    }

    @Override
    public Entity getRelatedMainEntity() {
        return this.mainEntity;
    }

    @Override
    public Entity getRelatedSubEntity() {
        return this.secondaryEntity;
    }

    public Condition getCondition() {
        return this.condition;
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

        argumentsMap.put("'Then' actions:", ((Integer) thenActions.size()).toString());
        argumentsMap.put("'Else' actions:", ((Integer) elseActions.size()).toString());

        if (this.condition instanceof MultipleCondition) {
            argumentsMap.put("Condition type:", "Multiple conditions");
            argumentsMap.put("Logical operation:", condition.getOperatorName());
            argumentsMap.put("Num of conditions:", condition.getNumOfConditions().toString());
        } else if (this.condition instanceof SingularCondition) {
            argumentsMap.put("Condition type:", "Singular condition");
            argumentsMap.put("Condition:", condition.describeCondition());
        }

        return argumentsMap;
    }

    @Override
    public void executeAction(Instance mainInstance)
            throws ConditionError, ActionException {
        if (condition == null || condition.isConditionMet(mainInstance)) {
            for (Action action : thenActions) {
                action.executeAction(mainInstance);
            }
        } else {
            if (elseActions != null) {
                for (Action action : elseActions) {
                    action.executeAction(mainInstance);
                }
            }
        }
    }

    @Override
    public void executeAction(Instance mainInstance, Instance secondaryInstance)
            throws ConditionError, ActionException {
        if (condition == null || condition.isConditionMet(mainInstance, secondaryInstance)) {
            for (Action action : thenActions) {
                action.executeAction(mainInstance, secondaryInstance);
            }
        } else {
            if (elseActions != null) {
                for (Action action : elseActions) {
                    action.executeAction(mainInstance, secondaryInstance);
                }
            }
        }
    }
}