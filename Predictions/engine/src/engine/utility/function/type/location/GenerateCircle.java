package engine.utility.function.type.location;

import engine.simulation.world.entity.location.Location;
import engine.simulation.world.grid.Grid;

import java.util.ArrayList;

public class GenerateCircle {
    private final Location location;
    private final Grid worldGrid;
    private final Integer radius;

    public GenerateCircle(Location location, Integer radius) {
        this.location = location;
        this.worldGrid = location.getWorldGrid();
        this.radius = radius;
    }

    public String getName() {
        return "generateCircle";
    }

    public ArrayList<Location> generateCircleLocations() {
        int moveUp, moveDown, moveRight, moveLeft;
        ArrayList<Location> circle = new ArrayList<>();

        if (location.getXValue() + radius > worldGrid.getXAxis()) {
            moveRight = location.getXValue() + radius - worldGrid.getXAxis();
        } else {
            moveRight = location.getXValue() + radius;
        }

        if (location.getXValue() - radius < 0) {
            moveLeft = location.getXValue() - radius + worldGrid.getXAxis();
        } else {
            moveLeft = location.getXValue() - radius;
        }

        if (location.getYValue() + radius > worldGrid.getYAxis()) {
            moveUp = location.getYValue() + radius - worldGrid.getYAxis();
        } else {
            moveUp = location.getYValue() + radius;
        }

        if (location.getYValue() - radius < 0) {
            moveDown = location.getYValue() - radius + worldGrid.getYAxis();
        } else {
            moveDown = location.getYValue() - radius;
        }

        Location moveOnlyUp = new Location(moveUp, location.getXValue());
        Location moveUpRight = new Location(moveUp, moveRight);
        Location moveOnlyRight = new Location(location.getYValue(), moveRight);
        Location moveDownRight = new Location(moveDown, moveRight);
        Location moveOnlyDown = new Location(moveDown, location.getXValue());
        Location moveDownLeft = new Location(moveDown, moveLeft);
        Location moveOnlyLeft = new Location(location.getYValue(), moveLeft);
        Location moveUpLeft = new Location(moveUp, moveLeft);

        circle.add(moveOnlyUp);
        circle.add(moveUpRight);
        circle.add(moveOnlyRight);
        circle.add(moveDownRight);
        circle.add(moveOnlyDown);
        circle.add(moveDownLeft);
        circle.add(moveOnlyLeft);
        circle.add(moveUpLeft);

        return circle;
    }
}
