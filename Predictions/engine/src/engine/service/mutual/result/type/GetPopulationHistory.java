package engine.service.mutual.result.type;

import engine.EngineService;
import engine.service.mutual.result.ResultRequest;
import engine.simulation.Simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GetPopulationHistory implements ResultRequest {
    private EngineService engine;

    public GetPopulationHistory() {
    }

    public GetPopulationHistory(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];
        Map<String, Map<Integer, Integer>> entitiesHistory = new HashMap<>();
        Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);

        optionalSimulation.ifPresent(simulation -> simulation.getWorld().getEntities().
                forEach(entity -> entitiesHistory.put(entity.getEntityName(), entity.getPopulationHistory())));

        return entitiesHistory;
    }
}
