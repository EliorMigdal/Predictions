package engine.simulation.world.rule.action.type;

import engine.jaxb.generated.PRDAction;
import engine.simulation.world.entity.Entity;
import engine.simulation.world.entity.exception.InvalidPopulation;
import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.entity.instance.exception.PropertyAlreadyExists;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.rule.action.Action;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.exception.ActionException;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Replace implements Action {
    private Entity killedEntity;
    private Entity createdEntity;
    private final String type;
    private final String mode;

    public Replace(PRDAction prdAction, Collection<Entity> entities) {
        this.type = prdAction.getType();
        this.mode = prdAction.getMode();

        for (Entity entity : entities) {
            if (entity.getEntityName().equals(prdAction.getKill())) {
                this.killedEntity = entity;
            } else if (entity.getEntityName().equals(prdAction.getCreate())) {
                this.createdEntity = entity;
            }
        }
    }

    @Override
    public void executeAction(Instance mainInstance)
            throws ActionException {
        try {
            mainInstance.kill();
            createdEntity.createNewInstance(mainInstance, mode);
            killedEntity.setEntityPopulation(killedEntity.getEntityPopulation() - 1);
        } catch (InvalidPropertyType | InvalidPropertyReference | IncompatibleTypes | PropertyAlreadyExists |
                 InvalidPopulation | CloneNotSupportedException | InvalidEntity exception) {
            throw new ActionException(this.type ,exception.getMessage());
        }
    }

    @Override
    public void executeAction(Instance mainInstance, Instance secondaryInstance)
            throws ActionException {
        try {
            mainInstance.kill();
            createdEntity.createNewInstance(mainInstance, mode);
            killedEntity.setEntityPopulation(killedEntity.getEntityPopulation() - 1);
        } catch (InvalidPropertyType | InvalidPropertyReference | IncompatibleTypes | PropertyAlreadyExists |
                 InvalidPopulation | CloneNotSupportedException | InvalidEntity exception) {
            throw new ActionException(this.type ,exception.getMessage());
        }
    }

    @Override
    public Entity getRelatedMainEntity() {
        return this.killedEntity;
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

    public String getMode() {
        return mode;
    }

    @Override
    public Map<String, String> getArgumentsMap() {
        Map<String,String> argumentsMap = new LinkedHashMap<>();
        argumentsMap.put("Replace:", killedEntity.getEntityName());
        argumentsMap.put("With:", createdEntity.getEntityName());
        argumentsMap.put("Mode:", getMode());
        return argumentsMap;
    }
}