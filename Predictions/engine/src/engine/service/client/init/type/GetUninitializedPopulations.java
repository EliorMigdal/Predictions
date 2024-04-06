package engine.service.client.init.type;

import engine.EngineService;
import engine.service.client.init.SimulationInitRequest;
import engine.simulation.Simulation;

import java.util.ArrayList;
import java.util.Optional;

public class GetUninitializedPopulations implements SimulationInitRequest {
    private EngineService engine;

    public GetUninitializedPopulations() {
    }

    public GetUninitializedPopulations(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];
        ArrayList<String> entityNames = new ArrayList<>();

        Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);

        optionalSimulation.ifPresent(simulation -> simulation.getWorld().getEntities()
                .stream().filter(entity -> entity.getEntityPopulation() == -1)
                .forEach(entity -> entityNames.add(entity.getEntityName())));

        return entityNames;
    }
}
