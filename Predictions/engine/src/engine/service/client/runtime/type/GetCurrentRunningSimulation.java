package engine.service.client.runtime.type;

import engine.EngineService;
import engine.client.request.Request;
import engine.service.client.runtime.RuntimeRequest;
import engine.simulation.Simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GetCurrentRunningSimulation implements RuntimeRequest {
    private EngineService engine;

    public GetCurrentRunningSimulation() {
    }

    public GetCurrentRunningSimulation(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];

        Optional<Request> optionalRequestData = engine.getRequestData(clientName, requestID);
        Map<Integer,String> returnValue = new HashMap<>();

        Optional<Simulation> optionalSimulation = optionalRequestData
                .flatMap(requestData -> requestData.getSimulations().stream()
                .filter(simulation -> !simulation.hasStartedRunning())
                .findFirst());

        optionalSimulation.ifPresent(simulation ->
                returnValue.put(simulation.getSimulationID() - 1, "Simulation ID: "
                        + (simulation.getSimulationID() - 1)));

        if (!optionalSimulation.isPresent()) {
            optionalRequestData.ifPresent(clientRequestData -> returnValue
                    .put(clientRequestData.getSimulations().size(), "Simulation ID: " +
                            clientRequestData.getSimulations().size()));
        }

        return returnValue;
    }
}
