package engine.simulation.world.entity.type;

import engine.jaxb.generated.PRDEntity;
import engine.jaxb.generated.PRDProperties;
import engine.jaxb.generated.PRDProperty;
import engine.simulation.world.entity.Entity;
import engine.simulation.world.entity.exception.InvalidPopulation;
import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.entity.instance.exception.PropertyAlreadyExists;
import engine.simulation.world.entity.location.Location;
import engine.simulation.world.grid.Grid;
import engine.simulation.world.grid.exception.NotEnoughSpace;
import engine.simulation.world.property.Property;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.property.factory.PropertyGenerator;
import engine.simulation.world.property.range.exception.RangeValueException;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainEntity implements Entity {
    private String entityName;
    private ArrayList<Instance> entityInstances;
    private final ArrayList<Property> entityProperties;
    private Integer entityPopulation = 0;
    private Grid worldGrid;
    private Map<Integer, Integer> populationHistory;

    public MainEntity(PRDEntity entity, Grid worldGrid)
            throws InvalidPropertyType, RangeValueException, ValueOutOfRange,
            InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        this.worldGrid = worldGrid;
        this.entityInstances = new ArrayList<>();
        this.entityProperties = new ArrayList<>();
        this.populationHistory = new LinkedHashMap<>();
        setEntityName(entity.getName());
        PRDProperties entityPrdProperties = entity.getPRDProperties();
        PropertyGenerator propertyGenerator = new PropertyGenerator();

        for (PRDProperty property : entityPrdProperties.getPRDProperty()) {
            Property newProperty = propertyGenerator.generateProperty(property.getType(), property);
            this.entityProperties.add(newProperty);
        }
    }

    @Override
    public Integer getEntityPopulation() {
        return entityPopulation;
    }

    @Override
    public void setEntityPopulation(int entityPopulation)
            throws InvalidPopulation {
        if (entityPopulation >= 0) {
            this.entityPopulation = entityPopulation;
        } else {
            resetPopulation();
            throw new InvalidPopulation(entityPopulation);
        }
    }

    @Override
    public ArrayList<Instance> getEntityInstances() {
        return this.entityInstances;
    }

    @Override
    public ArrayList<Property> getEntityProperties() {
        return this.entityProperties;
    }

    @Override
    public String getEntityName() {
        return entityName;
    }

    @Override
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public void initializePopulation(int population)
            throws NotEnoughSpace, InvalidPopulation {
        if (population >= 0) {
            this.worldGrid.initializePopulation(this.entityName, population);
            this.entityPopulation = population;
        } else {
            resetPopulation();
            throw new InvalidPopulation(population);
        }
    }

    @Override
    public Property searchForProperty(String propertyName) {
        return entityProperties.stream()
                .filter(property -> property.getPropertyName().equals(propertyName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void generateInstances()
            throws PropertyAlreadyExists, CloneNotSupportedException, InvalidPropertyReference,
            IncompatibleTypes, InvalidEntity {
        for (int i = 1; i <= this.entityPopulation; i++) {
            Instance currentInstance = new Instance(entityName, i, worldGrid);

            for (Property property : this.entityProperties) {
                Property newProp = property.clone(1);
                currentInstance.addProperty(newProp);
            }

            currentInstance.setRandomLocation();
            this.entityInstances.add(currentInstance);
        }
    }

    @Override
    public void resetPopulation() {
        this.entityPopulation = 0;
    }

    @Override
    public void createNewInstance(Instance formerInstance, String mode)
            throws CloneNotSupportedException, PropertyAlreadyExists, InvalidPropertyReference,
            IncompatibleTypes, InvalidEntity {
        Instance newInstance = new Instance(entityName, this.entityInstances.size() + 1, worldGrid);
        this.entityPopulation++;
        for (Property property : this.entityProperties) {
            Property newProperty = property.clone(1);
            newInstance.addProperty(newProperty);
        }

        if (mode.equalsIgnoreCase("scratch")) {
            newInstance.setRandomLocation();
        } else if (mode.equalsIgnoreCase("derived")) {
            Location newLocation = new Location(formerInstance.getInstanceLocation().getYValue(),
                    formerInstance.getInstanceLocation().getXValue(), formerInstance.getInstanceLocation().getWorldGrid());
            newInstance.setInstanceLocation(newLocation);
            try {
                newInstance.findAndReplace(formerInstance.getProperties());
            } catch (ValueOutOfRange ignored) {

            }
        }

        this.entityInstances.add(newInstance);
    }

    @Override
    public Map<Integer, Integer> getPopulationHistory() {
        return this.populationHistory;
    }

    @Override
    public void updatePopulationHistory(Integer tickNumber) {
        this.populationHistory.put(tickNumber, this.entityPopulation);
    }

    @Override
    public void updateInstances() {
        ArrayList<Instance> updatedInstances = new ArrayList<>();

        for (Instance instance : this.entityInstances) {
            if (instance.isAlive()) {
                updatedInstances.add(instance);
            }
        }

        this.entityInstances = updatedInstances;
    }

    @Override
    public Entity clone() {
        try {
            MainEntity clonedEntity = (MainEntity) super.clone();
            clonedEntity.entityName = this.entityName;
            clonedEntity.entityInstances = new ArrayList<>();
            for (Instance instance : this.entityInstances) {
                clonedEntity.entityInstances.add(instance.clone());
            }
            clonedEntity.worldGrid = null;
            clonedEntity.entityPopulation = this.entityPopulation;
            clonedEntity.populationHistory = new HashMap<>(this.populationHistory);
            return clonedEntity;
        } catch (CloneNotSupportedException exception) {
            throw new RuntimeException(exception);
        }
    }
}