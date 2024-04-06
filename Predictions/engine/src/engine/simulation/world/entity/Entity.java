package engine.simulation.world.entity;

import engine.simulation.world.entity.exception.InvalidPopulation;
import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.entity.instance.exception.PropertyAlreadyExists;
import engine.simulation.world.grid.exception.NotEnoughSpace;
import engine.simulation.world.property.Property;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.rule.action.condition.exception.ConditionError;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public interface Entity extends Serializable, Cloneable {
    String getEntityName();
    void setEntityName(String entityName);
    void initializePopulation(int population) throws NotEnoughSpace, InvalidPopulation;
    Integer getEntityPopulation();
    void setEntityPopulation(int entityPopulation)
            throws InvalidPopulation;
    ArrayList<Instance> getEntityInstances();
    ArrayList<Property> getEntityProperties();
    Property searchForProperty(String propertyName);
    void generateInstances()
            throws PropertyAlreadyExists, CloneNotSupportedException, InvalidPropertyReference,
            IncompatibleTypes, ConditionError, InvalidEntity;
    void resetPopulation();
    void createNewInstance(Instance formerInstance, String mode)
            throws CloneNotSupportedException, PropertyAlreadyExists, InvalidPropertyReference,
            IncompatibleTypes, InvalidPropertyType, InvalidEntity;
    Map<Integer, Integer> getPopulationHistory();
    void updatePopulationHistory(Integer tickNumber);
    void updateInstances();
    Entity clone();
}