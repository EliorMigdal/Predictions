package engine.service.client.init.type;

import engine.EngineService;
import engine.service.client.init.SimulationInitRequest;
import engine.simulation.Simulation;
import engine.simulation.world.entity.exception.InvalidPopulation;
import engine.simulation.world.grid.exception.NotEnoughSpace;

import java.util.Optional;

public class InitializePopulation implements SimulationInitRequest {
    private EngineService engine;

    public InitializePopulation() {
    }

    public InitializePopulation(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];
        String entityName = (String) args[3];
        Integer newPopulation = (Integer) args[4];
        Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);

        return optionalSimulation.<Object>map(simulation -> simulation.getWorld().getEntities().stream()
                .filter(entity -> entity.getEntityName().equals(entityName))
                .allMatch(entity -> {
                    try {
                        entity.initializePopulation(newPopulation);
                        return true;
                    } catch (InvalidPopulation | NotEnoughSpace exception) {
                        return false;
                    }
                }));
    }
}