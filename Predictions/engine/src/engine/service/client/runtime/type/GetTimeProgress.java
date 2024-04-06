package engine.service.client.runtime.type;

import engine.EngineService;
import engine.service.client.runtime.RuntimeRequest;
import engine.simulation.Simulation;

import java.util.Optional;

public class GetTimeProgress implements RuntimeRequest {
    private EngineService engine;

    public GetTimeProgress() {
    }

    public GetTimeProgress(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];

        Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);
        return Integer.toString(new Double(optionalSimulation.map(Simulation::getCurrentTick).orElse(0)).intValue());
    }
}
