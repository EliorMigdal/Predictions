package engine.simulation.time;

import engine.simulation.time.type.History;
import engine.simulation.time.type.PauseTime;
import engine.simulation.time.type.RunTime;
import engine.simulation.world.World;

public class Time {
    private final RunTime simulationRunTime;
    private final PauseTime simulationPauseTime;
    private final History simulationHistory;

    public Time() {
        this.simulationRunTime = new RunTime();
        this.simulationPauseTime = new PauseTime();
        this.simulationHistory = new History();
    }

    public RunTime getSimulationRunTime() {
        return simulationRunTime;
    }

    public PauseTime getSimulationPauseTime() {
        return simulationPauseTime;
    }

    public void updateHistory(Integer tick, World world) {
        this.simulationHistory.updateHistory(tick, world);
    }

    public double getRunTime() {
        return this.simulationRunTime.getRunTime();
    }

    public double getTimeProgress() {
        return this.simulationRunTime.getTimeProgress(simulationPauseTime);
    }
}
