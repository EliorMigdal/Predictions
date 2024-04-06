package engine.simulation.world.entity.instance;

import engine.simulation.world.entity.instance.exception.PropertyAlreadyExists;
import engine.simulation.world.entity.instance.exception.PropertyNotFound;
import engine.simulation.world.entity.location.Location;
import engine.simulation.world.grid.Grid;
import engine.simulation.world.property.Property;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Instance implements Serializable, Cloneable {
    private ArrayList<Property> properties;
    private Location instanceLocation;
    private String entityName;
    private Integer instanceID;
    private Boolean isAlive = true;

    public Instance(String entityName, Integer instanceID, Grid worldGrid) {
        this.entityName = entityName;
        this.instanceID = instanceID;
        this.properties = new ArrayList<>();
        this.instanceLocation = new Location(worldGrid);
    }

    public Collection<Property> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Property> properties) {
        this.properties = properties;
    }

    public Boolean isAlive() {
        return isAlive;
    }

    public void kill() {
        isAlive = false;
        instanceLocation.removeLocationFromGrid(this, instanceLocation);
    }

    public Property searchForProperty(String propertyName) {
        if (properties.size() > 0) {
            return properties.stream()
                    .filter(property -> property.getPropertyName().equals(propertyName))
                    .findFirst()
                    .orElse(null);
        } else {
            return null;
        }
    }

    public Object getPropertyValue(String propertyName)
            throws PropertyNotFound {
        Object output;
        Property desiredProperty = searchForProperty(propertyName);

        if (desiredProperty != null) {
            output = desiredProperty.getPropertyValue();
        } else {
            throw new PropertyNotFound(propertyName);
        }

        return output;
    }

    public void addProperty(Property property)
            throws PropertyAlreadyExists {
        Property desiredProperty = searchForProperty(property.getPropertyName());

        if (desiredProperty != null) {
            throw new PropertyAlreadyExists(property.getPropertyName());
        } else {
            properties.add(property);
        }
    }

    public void move()
            throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        this.instanceLocation.move(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instance instance = (Instance) o;
        return Objects.equals(entityName, instance.entityName) && Objects.equals(instanceID, instance.instanceID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityName, instanceID);
    }

    public void setRandomLocation()
            throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        this.instanceLocation.randomiseLocation(this);
    }

    public String getEntityName() {
        return entityName;
    }

    public void tick(Integer tickNumber)
            throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        for (Property property : this.properties) {
            property.increaseTicksCount();
            property.updateHistory(tickNumber);
        }

        this.move();
    }

    public Location getInstanceLocation() {
        return instanceLocation;
    }

    public void setInstanceLocation(Location instanceLocation) {
        this.instanceLocation = instanceLocation;
        this.instanceLocation.addLocationToGrid(this, instanceLocation);
    }

    public void findAndReplace(Collection<Property> newProperties)
            throws ValueOutOfRange {
        ArrayList<Property> updatedProperties = new ArrayList<>();
        for (Property newProperty : newProperties) {
            for (Property property : properties) {
                if (property.equals(newProperty)) {
                    updatedProperties.add(newProperty);
                }
            }
        }

        if (updatedProperties.size() != properties.size()) {
            for (Property property : properties) {
                if (!updatedProperties.contains(property)) {
                    updatedProperties.add(property);
                }
            }
        }

        this.properties = updatedProperties;
    }

    @Override
    public Instance clone() {
        try {
            Instance clonedInstance = (Instance) super.clone();
            clonedInstance.properties = new ArrayList<>();

            for (Property property : this.properties) {
                clonedInstance.properties.add(property.clone());
            }

            clonedInstance.instanceLocation = this.instanceLocation.clone();
            clonedInstance.instanceID = this.instanceID;
            clonedInstance.isAlive = this.isAlive;
            clonedInstance.entityName = this.entityName;

            return clonedInstance;
        } catch (CloneNotSupportedException exception) {
            throw new AssertionError(exception);
        }
    }
}