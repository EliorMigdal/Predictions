package engine.simulation.task;

import engine.client.Client;
import engine.simulation.Simulation;
import engine.simulation.exception.runtime.SimulationRuntimeException;
import engine.version.VersionManager;
import engine.version.types.usage.ClientUsageUpdate;

public class RunSimulationTask implements Runnable {
    private final Simulation currentSimulation;
    private final VersionManager versionManager;
    private final Client client;

    public RunSimulationTask(Simulation currentSimulation, VersionManager versionManager, Client client) {
        this.currentSimulation = currentSimulation;
        this.versionManager = versionManager;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            currentSimulation.setQueueStatus(false);
            currentSimulation.setRunStatus(true);

            client.getUsageData().decrementNumOfWaiting();
            client.getUsageData().incrementNumOfRunning();

            versionManager.addNewUsageData(new ClientUsageUpdate(client.getClientName(), client.getUsageData()));

            currentSimulation.runSimulation();
        } catch (SimulationRuntimeException exception) {
            currentSimulation.setFinishStatus(true);
        } finally {
            client.getUsageData().decrementNumOfRunning();
            client.getUsageData().incrementNumOfFinished();
            versionManager.addNewUsageData(new ClientUsageUpdate(client.getClientName(), client.getUsageData()));
        }
    }
}