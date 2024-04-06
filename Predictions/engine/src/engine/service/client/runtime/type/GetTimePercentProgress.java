package engine.service.client.runtime.type;

import engine.EngineService;
import engine.service.client.runtime.RuntimeRequest;
import engine.simulation.Simulation;
import engine.simulation.termination.type.ByTime;

import java.util.Optional;

public class GetTimePercentProgress implements RuntimeRequest {

    public GetTimePercentProgress() {
    }

    private EngineService engine;

    public GetTimePercentProgress(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];

        Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);

        return optionalSimulation.flatMap(simulation -> simulation.getTerminationsTerms().stream()
                        .filter(termination -> termination instanceof ByTime).findFirst()
                        .map(termination -> simulation.getTimeProgress() / (Integer) termination.getTerminationTerm()))
                .orElse(null);
    }
}