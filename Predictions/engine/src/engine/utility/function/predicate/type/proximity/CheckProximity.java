package engine.utility.function.predicate.type.proximity;

import engine.simulation.world.entity.location.Location;
import engine.utility.function.predicate.Predicate;
import engine.utility.function.type.location.GenerateCircle;

import java.util.ArrayList;

public class CheckProximity implements Predicate {
    private final Location firstLocation;
    private final Location secondLocation;
    private final Number circularDistance;

    public CheckProximity(Location firstLocation, Location secondLocation, Number circularDistance) {
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;
        this.circularDistance = circularDistance;
    }

    @Override
    public boolean run() {
        int distance = circularDistance.intValue();

        for (int i = 0; i <= distance; i++) {
            ArrayList<Location> circleLocations = new GenerateCircle(firstLocation, i)
                    .generateCircleLocations();

            for (Location location : circleLocations) {
                if (location.equals(secondLocation)) {
                    return true;
                }
            }
        }

        return false;
    }
}