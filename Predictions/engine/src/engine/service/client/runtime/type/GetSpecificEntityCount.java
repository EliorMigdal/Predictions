package engine.service.client.runtime.type;

import engine.EngineService;
import engine.service.client.runtime.RuntimeRequest;
import engine.simulation.Simulation;

import java.util.Optional;

public class GetSpecificEntityCount implements RuntimeRequest {
    private EngineService engine;

    public GetSpecificEntityCount() {
    }

    public GetSpecificEntityCount(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];
        String entityName = (String) args[3];

        Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);
        return optionalSimulation.flatMap(simulation -> simulation.getWorld().getEntities().stream()
                .filter(entity -> entity.getEntityName().equals(entityName)).findFirst())
                .map(entity -> entity.getEntityPopulation().toString()).orElse(null);
    }
}