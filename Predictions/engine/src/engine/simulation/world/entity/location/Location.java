package engine.simulation.world.entity.location;

import engine.simulation.world.entity.instance.Instance;
import engine.simulation.world.grid.Grid;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;
import engine.utility.function.type.random.GenerateRandomInteger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Location implements Cloneable {
    private Integer YValue;
    private Integer XValue;
    private Grid worldGrid;

    public Location(Grid grid) {
        this.worldGrid = grid;
    }

    public Location(Integer YValue, Integer XValue) {
        this.YValue = YValue;
        this.XValue = XValue;
    }

    public Location(Integer YValue, Integer XValue, Grid worldGrid) {
        this.YValue = YValue;
        this.XValue = XValue;
        this.worldGrid = worldGrid;
    }

    public Integer getYValue() {
        return YValue;
    }

    public Integer getXValue() {
        return XValue;
    }

    public Grid getWorldGrid() {
        return worldGrid;
    }

    public void setYValue(Integer YValue) {
        this.YValue = YValue;
    }

    public void setXValue(Integer XValue) {
        this.XValue = XValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(YValue, location.YValue) && Objects.equals(XValue, location.XValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(YValue, XValue);
    }

    public void move(Instance instance)
            throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        Function generateRandomInteger = new GenerateRandomInteger(4, 1);
        Integer movingDirection = (Integer) generateRandomInteger.run();
        Integer newDirection = movingDirection;
        boolean attemptResult = false;
        int moveAttempts = 0;
        Map<Integer, Boolean> attemptsResults = new HashMap<>();
        Location newLocation = null;

        while (moveAttempts < 4 && !attemptResult) {
            if (movingDirection == 1) {
                newLocation = new Location(this.YValue + 1, this.XValue, worldGrid);
            } else if (movingDirection == 2) {
                newLocation = new Location(this.YValue, this.XValue + 1, worldGrid);
            } else if (movingDirection == 3) {
                newLocation = new Location(this.YValue - 1, this.XValue, worldGrid);
            } else if (movingDirection == 4) {
                newLocation = new Location(this.YValue, this.XValue - 1, worldGrid);
            }

            if (newLocation != null) {
                attemptResult = this.worldGrid.insertNewLocation(instance, newLocation);
            }

            if (!attemptResult) {
                attemptsResults.put(movingDirection, false);
                moveAttempts++;

                while (newDirection.equals(movingDirection) && moveAttempts < 4) {
                    newDirection = (Integer) generateRandomInteger.run();
                    if (attemptsResults.get(newDirection) != null) {
                        newDirection = movingDirection;
                    }
                }

                movingDirection = newDirection;
            } else {
                instance.setInstanceLocation(newLocation);
            }
        }
    }

    public void randomiseLocation(Instance instance)
            throws InvalidPropertyReference, IncompatibleTypes, InvalidEntity {
        Function generateRandomYValue = new GenerateRandomInteger(worldGrid.getYAxis(), 0);
        Function generateRandomXValue = new GenerateRandomInteger(worldGrid.getXAxis(), 0);
        Integer randomYValue = (Integer) generateRandomYValue.run();
        Integer randomXValue = (Integer) generateRandomXValue.run();

        while (worldGrid.isOccupied(new Location(randomYValue, randomXValue))) {
            randomYValue = (Integer) generateRandomYValue.run();
            randomXValue = (Integer) generateRandomXValue.run();
        }

        this.setYValue(randomYValue);
        this.setXValue(randomXValue);
        worldGrid.insertNewLocation(instance, this);
    }

    public Location clone() {
        try {
            Location clonedLocation =  (Location) super.clone();
            clonedLocation.worldGrid = null;
            return clonedLocation;
        } catch (CloneNotSupportedException exception) {
            throw new AssertionError(exception);
        }
    }

    public void addLocationToGrid(Instance instance, Location location) {
        this.worldGrid.insertNewLocation(instance, location);
    }

    public void removeLocationFromGrid(Instance instance, Location location) {
        this.worldGrid.removeLocation(instance, location);
    }
}