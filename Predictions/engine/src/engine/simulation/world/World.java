package engine.simulation.world;

import engine.jaxb.generated.*;
import engine.simulation.exception.creation.EntityCreationException;
import engine.simulation.exception.creation.EnvironmentCreationException;
import engine.simulation.exception.creation.RuleCreationException;
import engine.simulation.exception.runtime.SimulationRuntimeException;
import engine.simulation.world.entity.Entity;
import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.entity.instance.exception.PropertyAlreadyExists;
import engine.simulation.world.entity.type.MainEntity;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.environment.exception.InvalidEnvironmentName;
import engine.simulation.world.environment.type.EnvironmentVariable;
import engine.simulation.world.grid.Grid;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.property.range.exception.RangeValueException;
import engine.simulation.world.rule.Rule;
import engine.simulation.world.rule.action.Action;
import engine.simulation.world.rule.action.condition.exception.ConditionError;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.exception.ActionException;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.simulation.world.rule.activation.exception.InvalidProbability;
import engine.simulation.world.rule.activation.exception.InvalidTicksValue;
import engine.simulation.world.rule.type.MainRule;
import engine.utility.function.exception.InvalidPropertyReference;

import java.io.Serializable;
import java.util.ArrayList;

public class World implements Serializable, Cloneable {
    private ArrayList<Entity> entities;
    private final ArrayList<Rule> rules;
    private final Environment environmentVariables;
    private Grid worldGrid;

    public World(PRDWorld world)
            throws EntityCreationException, EnvironmentCreationException, RuleCreationException {
        environmentVariables = new EnvironmentVariable(world.getPRDEnvironment());
        Grid worldGrid = new Grid(world.getPRDGrid().getRows(), world.getPRDGrid().getColumns());
        this.worldGrid = worldGrid;

        this.entities = new ArrayList<>();
        PRDEntities prdEntities = world.getPRDEntities();
        for (PRDEntity entity : prdEntities.getPRDEntity()) {
            try {
                Entity newEntity = new MainEntity(entity, worldGrid);
                this.entities.add(newEntity);
            } catch (InvalidPropertyType | RangeValueException | InvalidPropertyReference | IncompatibleTypes |
                     ValueOutOfRange | InvalidEntity exception) {
                throw new EntityCreationException(entity.getName(), exception.getMessage());
            }
        }

        this.rules = new ArrayList<>();
        PRDRules prdRules = world.getPRDRules();
        for (PRDRule prdRule : prdRules.getPRDRule()) {
            try {
                Rule newRule = new MainRule(prdRule, entities, environmentVariables);
                this.rules.add(newRule);
            } catch (InvalidTicksValue | InvalidProbability exception) {
                throw new RuleCreationException(prdRule.getName(), exception.getMessage());
            }
        }
    }

    public ArrayList<Entity> getEntities() {
        return this.entities;
    }

    public ArrayList<Rule> getRules() {
        return this.rules;
    }

    public Environment getEnvironmentVariables() {
        return environmentVariables;
    }

    public void setEnvironmentVariables(Environment environmentVariables) {
        environmentVariables.getProperties().forEach(property -> {
            try {
                this.environmentVariables.setPropertyValue(property.getPropertyName(),property.getPropertyValue());
            } catch (InvalidEnvironmentName | InvalidPropertyType | ValueOutOfRange | InvalidPropertyReference |
                     IncompatibleTypes | InvalidEntity e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void tick(Integer tickNumber)
            throws SimulationRuntimeException {

        try {
            ArrayList<Action> activatedActions = new ArrayList<>();
            for (Rule rule : this.rules) {
                if (rule.checkForActivation(tickNumber)) {
                    activatedActions.addAll(rule.getActions());
                }
            }

            for (Entity entity : this.entities) {
                if (tickNumber == 1) {
                    entity.generateInstances();
                } else {
                    for (Instance instance : entity.getEntityInstances()) {
                        instance.tick(tickNumber);
                    }
                }
                entity.updatePopulationHistory(tickNumber);
            }

            for (Entity entity : this.entities) {
                for (Action action : activatedActions) {
                    if (action.getRelatedMainEntity().getEntityName().equals(entity.getEntityName())) {
                        for (Instance mainInstance : entity.getEntityInstances()) {
                            if (action.getRelatedSubEntity() != null) {
                                action.getRelatedSubEntity().generateInstances();
                                ArrayList<Instance> subInstances = action.getRelatedSubEntity().getEntityInstances();

                                for (Instance subInstance : subInstances) {
                                    if (mainInstance.isAlive() && subInstance.isAlive()) {
                                        action.executeAction(mainInstance, subInstance);
                                    }
                                }
                            } else {
                                if (mainInstance.isAlive()) {
                                    action.executeAction(mainInstance);
                                }
                            }
                        }
                    }
                }
            }

            for (Entity entity : entities) {
                entity.updateInstances();
            }
        } catch (ConditionError | InvalidEnvironmentName | InvalidPropertyReference | ActionException |
                 IncompatibleTypes | PropertyAlreadyExists | CloneNotSupportedException | InvalidEntity exception) {
            throw new SimulationRuntimeException(exception.getMessage());
        }
    }

    public Entity searchForEntity(String entityName) {
        for (Entity entity : entities) {
            if (entityName.equals(entity.getEntityName())) {
                return entity;
            }
        }

        return null;
    }

    @Override
    public World clone() {
        try {
            World clonedWorld = (World) super.clone();
            clonedWorld.entities = new ArrayList<>();
            clonedWorld.worldGrid = new Grid(this.worldGrid.getYAxis(), this.worldGrid.getXAxis());
            for (Entity entity : this.entities) {
                clonedWorld.entities.add(entity.clone());
            }

            for (Entity entity : clonedWorld.entities) {
                for (Instance instance : entity.getEntityInstances()) {
                    clonedWorld.worldGrid.insertNewLocation(instance, instance.getInstanceLocation());
                }
            }
            return clonedWorld;
        } catch (CloneNotSupportedException exception) {
            throw new AssertionError();
        }
    }

    public Grid getWorldGrid() {
        return worldGrid;
    }
}