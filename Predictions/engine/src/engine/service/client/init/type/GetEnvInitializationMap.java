package engine.service.client.init.type;

import engine.EngineService;
import engine.service.client.init.SimulationInitRequest;
import engine.simulation.Simulation;
import engine.simulation.world.World;
import engine.simulation.world.environment.Environment;

import java.util.Optional;

public class GetEnvInitializationMap implements SimulationInitRequest {
    private EngineService engine;

    public GetEnvInitializationMap() {
    }

    public GetEnvInitializationMap(EngineService engineService) {
        this.engine = engineService;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];

        Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);

        return optionalSimulation
                .map(Simulation::getWorld)
                .map(World::getEnvironmentVariables)
                .map(Environment::getInitializedMap)
                .orElse(null);
    }
}
