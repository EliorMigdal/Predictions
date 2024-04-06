package engine.simulation.time.type;

import engine.simulation.world.World;

import java.util.LinkedHashMap;
import java.util.Map;

public class History {
    private final Map<Integer, World> simulationHistory;

    public History() {
        this.simulationHistory = new LinkedHashMap<>();
    }

    public Map<Integer, World> getHistoryMap() {
        return simulationHistory;
    }

    public void updateHistory(Integer tick, World world) {
        this.simulationHistory.put(tick, world);
    }
}
