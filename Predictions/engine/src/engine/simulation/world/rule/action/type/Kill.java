package engine.simulation.world.rule.action.type;

import engine.jaxb.generated.PRDAction;
import engine.simulation.world.entity.Entity;
import engine.simulation.world.entity.exception.InvalidPopulation;
import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.entity.type.SecondaryEntity;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.rule.action.Action;
import engine.simulation.world.rule.action.exception.ActionException;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Kill implements Action {
    private Entity mainEntity;
    private Entity secondaryEntity = null;
    private final String type;

    public Kill(PRDAction prdAction, Collection<Entity> entities, Environment environment) {
        this.type = prdAction.getType();

        for (Entity entity : entities) {
            if (entity.getEntityName().equals(prdAction.getEntity())) {
                this.mainEntity = entity;
            } else if (prdAction.getPRDSecondaryEntity() != null && entity.getEntityName()
                    .equals(prdAction.getPRDSecondaryEntity().getEntity())) {
                this.secondaryEntity = new SecondaryEntity(prdAction.getPRDSecondaryEntity(), entities, environment);
            }
        }
    }

    public Entity getRelatedMainEntity() {
        return this.mainEntity;
    }

    @Override
    public Entity getRelatedSubEntity() {
        return this.secondaryEntity;
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
        argumentsMap.put("Kill:", mainEntity.getEntityName());
        return argumentsMap;
    }

    @Override
    public void executeAction(Instance mainInstance)
            throws ActionException {
        try {
            mainInstance.kill();
            mainEntity.setEntityPopulation(mainEntity.getEntityPopulation() - 1);
        } catch (InvalidPopulation exception) {
            throw new ActionException(this.type, exception.getMessage());
        }
    }

    @Override
    public void executeAction(Instance mainInstance, Instance secondaryInstance)
            throws ActionException {
        try {
            mainInstance.kill();
            mainEntity.setEntityPopulation(mainEntity.getEntityPopulation() - 1);
        } catch (InvalidPopulation exception) {
            throw new ActionException(this.type, exception.getMessage());
        }
    }
}