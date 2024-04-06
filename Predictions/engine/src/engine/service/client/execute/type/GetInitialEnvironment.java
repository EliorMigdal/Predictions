package engine.service.client.execute.type;

import engine.EngineService;
import engine.service.client.execute.SimulationExecuteRequest;
import engine.simulation.Simulation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class GetInitialEnvironment implements SimulationExecuteRequest {
    private EngineService engine;

    public GetInitialEnvironment() {
    }

    public GetInitialEnvironment(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];
        Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);
        Map<String,String> initialEnvironments = new LinkedHashMap<>();

        optionalSimulation.ifPresent(simulation -> simulation.getWorld().getEnvironmentVariables().getProperties()
                .forEach(property -> initialEnvironments
                        .put(property.getPropertyName(), property.getPropertyFirstTickValue())));

        return initialEnvironments;
    }
}
