package engine.simulation.world.grid;

import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.entity.location.Location;
import engine.simulation.world.grid.exception.NotEnoughSpace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Grid implements Cloneable {
    private final Integer YAxis;
    private final Integer XAxis;
    private final ArrayList<Location> occupiedLocations;
    private final Map<Instance, Location> instanceLocationMap;
    private final Map<String, Integer> initialPopulation;

    public Grid(Integer YAxis, Integer XAxis) {
        this.YAxis = YAxis;
        this.XAxis = XAxis;
        this.occupiedLocations = new ArrayList<>();
        this.instanceLocationMap = new HashMap<>();
        this.initialPopulation = new LinkedHashMap<>();
    }

    public Integer getYAxis() {
        return YAxis;
    }

    public Integer getXAxis() {
        return XAxis;
    }

    public ArrayList<Location> getOccupiedLocations() {
        return occupiedLocations;
    }

    public Map<Instance, Location> getInstanceLocationMap() {
        return instanceLocationMap;
    }

    public boolean insertNewLocation(Instance instance, Location newLocation) {
        if (newLocation.getYValue().equals(getYAxis() + 1)) {
            newLocation.setYValue(0);
        } else if (newLocation.getYValue().equals(-1)) {
            newLocation.setYValue(getYAxis());
        }

        if (newLocation.getXValue().equals(getYAxis() + 1)) {
            newLocation.setXValue(0);
        } else if (newLocation.getXValue().equals(-1)) {
            newLocation.setXValue(getXAxis());
        }

        for (Location location : getOccupiedLocations()) {
            if (location.equals(newLocation)) {
                return false;
            }
        }

        if (instanceLocationMap.get(instance) != null) {
            occupiedLocations.remove(instance.getInstanceLocation());
        }

        getOccupiedLocations().add(newLocation);
        getInstanceLocationMap().put(instance, newLocation);
        return true;
    }

    public boolean isOccupied(Location location) {
        return occupiedLocations.contains(location);
    }

    public void removeLocation(Instance instance, Location location) {
        if (instanceLocationMap.get(instance) != null) {
            instanceLocationMap.remove(instance);
        }

        occupiedLocations.remove(location);
    }

    @Override
    public Grid clone() {
        try {
            return (Grid) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void initializePopulation(String entityName, Integer population)
            throws NotEnoughSpace {
        Integer populationCounter = 0;

        for (String entity : this.initialPopulation.keySet()) {
            populationCounter += this.initialPopulation.get(entity);
        }

        if (this.initialPopulation.get(entityName) == null) {
            if (populationCounter + population > YAxis * XAxis) {
                throw new NotEnoughSpace((YAxis * XAxis) - populationCounter);
            } else {
                this.initialPopulation.put(entityName, population);
            }
        } else if (this.initialPopulation.get(entityName) != null) {
            if (populationCounter + population - this.initialPopulation.get(entityName) > YAxis * XAxis) {
                throw new NotEnoughSpace((YAxis * XAxis) - populationCounter);
            } else {
                this.initialPopulation.put(entityName, population);
            }
        }
    }
}