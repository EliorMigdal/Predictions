package engine.simulation.world.entity.type;

import engine.jaxb.generated.PRDAction;
import engine.simulation.world.entity.Entity;
import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.entity.type.selection.Selection;
import engine.simulation.world.environment.Environment;
import engine.simulation.world.property.Property;
import engine.simulation.world.rule.action.condition.exception.ConditionError;
import engine.simulation.world.rule.action.condition.factory.ConditionGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class SecondaryEntity implements Entity {
    private String entityName;
    private Entity relatedEntity;
    private ArrayList<Instance> finalInstances;
    private final Selection selection;

    public SecondaryEntity(PRDAction.PRDSecondaryEntity secondaryEntity, Collection<Entity> entities,
                           Environment environmentVariables) {
        this.entityName = secondaryEntity.getEntity();
        for (Entity entity : entities) {
            if (entity.getEntityName().equals(this.entityName)) {
                this.relatedEntity = entity;
            }
        }

        if (secondaryEntity.getPRDSelection().getPRDCondition() != null) {
            ConditionGenerator conditionGenerator = new ConditionGenerator();
            this.selection = new Selection(secondaryEntity.getPRDSelection().getCount(),
                    conditionGenerator.generateCondition(secondaryEntity.getPRDSelection().getPRDCondition(),
                            entities, environmentVariables));
        } else {
            this.selection = new Selection(secondaryEntity.getPRDSelection().getCount());
        }
    }

    @Override
    public String getEntityName() {
        return this.entityName;
    }

    @Override
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public void initializePopulation(int population) {

    }

    @Override
    public Integer getEntityPopulation() {
        return this.finalInstances.size();
    }

    @Override
    public void setEntityPopulation(int entityPopulation) {

    }

    @Override
    public ArrayList<Instance> getEntityInstances() {
        return finalInstances;
    }

    @Override
    public ArrayList<Property> getEntityProperties() {
        return relatedEntity.getEntityProperties();
    }

    @Override
    public Property searchForProperty(String propertyName) {
        return relatedEntity.searchForProperty(propertyName);
    }

    @Override
    public void generateInstances() {
        this.finalInstances = new ArrayList<>();
        String selectionCount = this.selection.getCount();

        if (selectionCount.equalsIgnoreCase("All")) {
            this.finalInstances = this.relatedEntity.getEntityInstances().stream()
                    .filter(Instance::isAlive).collect(Collectors.toCollection(ArrayList::new));
        } else {
            Collections.shuffle(this.relatedEntity.getEntityInstances());
            int selectionNum = Integer.parseInt(selectionCount);

            this.finalInstances = this.relatedEntity.getEntityInstances().stream()
                    .filter(instance -> {
                        try {
                            return instance.isAlive() && selection.isConditionMet(instance);
                        } catch (ConditionError e) {
                            throw new RuntimeException(e);
                        }
                    }).limit(selectionNum).collect(Collectors.toCollection(ArrayList::new));

            while (this.finalInstances.size() < selectionNum && this.finalInstances.size() > 0) {
                this.finalInstances.add(this.finalInstances.get(0).clone());
            }
        }
    }

    @Override
    public void resetPopulation() {
        this.finalInstances.clear();
        this.finalInstances = new ArrayList<>();
    }

    @Override
    public void createNewInstance(Instance formerInstance, String mode)  {

    }



    @Override
    public Map<Integer, Integer> getPopulationHistory() {
        return null;
    }

    @Override
    public void updatePopulationHistory(Integer tickNumber) {

    }

    @Override
    public void updateInstances() {

    }

    @Override
    public Entity clone() {
        return null;
    }
}