package engine.service.client.runtime.type;

import engine.EngineService;
import engine.service.client.runtime.RuntimeRequest;
import engine.simulation.Simulation;
import engine.simulation.termination.type.ByTicks;

import java.util.Optional;

public class GetTicksPercentProgress implements RuntimeRequest {
    private EngineService engine;

    public GetTicksPercentProgress() {
    }

    public GetTicksPercentProgress(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];

        Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);

        return optionalSimulation.flatMap(simulation -> simulation.getTerminationsTerms().stream()
                .filter(termination -> termination instanceof ByTicks).findFirst()
                .map(termination -> simulation.getCurrentTick().doubleValue() / (Integer) termination.getTerminationTerm()))
                .orElse(null);
    }
}