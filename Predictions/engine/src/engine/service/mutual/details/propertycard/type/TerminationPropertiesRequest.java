package engine.service.mutual.details.propertycard.type;

import engine.EngineService;
import engine.service.mutual.details.propertycard.PropertyCardRequest;
import engine.simulation.Simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TerminationPropertiesRequest implements PropertyCardRequest {
    private EngineService engine;

    public TerminationPropertiesRequest() {
    }

    public TerminationPropertiesRequest(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];
        String terminationName = (String) args[3];

        Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);
        Map<String, String> termDetails = new HashMap<>();

        optionalSimulation.flatMap(simulation -> simulation.getTerminationsTerms().stream()
                .filter(termination -> termination.getTerminationName().equals(terminationName)).findFirst())
                .ifPresent(termination -> {
                    termDetails.put("Type", termination.getTerminationName());
                    termDetails.put("Value", termination.getTerminationTerm().toString());
                });

        return termDetails;
    }
}