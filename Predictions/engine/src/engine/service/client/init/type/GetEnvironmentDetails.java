package engine.service.client.init.type;

import engine.EngineService;
import engine.service.client.init.SimulationInitRequest;
import engine.simulation.Simulation;
import engine.simulation.world.property.Property;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class GetEnvironmentDetails implements SimulationInitRequest {
    private EngineService engine;

    public GetEnvironmentDetails() {
    }

    public GetEnvironmentDetails(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];
        String environmentName = (String) args[3];

        Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);
        Map<String, String> detailsMap = new LinkedHashMap<>();

        optionalSimulation.ifPresent(simulation -> {
            Optional<Property> optionalEnvironment = simulation.getWorld().getEnvironmentVariables()
                    .getProperties().stream()
                    .filter(property -> property.getPropertyName().equals(environmentName))
                    .findFirst();

            optionalEnvironment.ifPresent(environment -> {
                if (environment.hasRange()) {
                    detailsMap.put(environment.getPropertyValue().getClass().getSimpleName(),
                            "From " + environment.getPropertyRange().getMinValue().toString() + " to "
                                    + environment.getPropertyRange().getMaxValue().toString());
                } else {
                    if (environment.getPropertyValue() != null) {
                        detailsMap.put(environment.getPropertyValue().getClass().getSimpleName(), "None");
                    } else {
                        detailsMap.put("None","None");
                    }
                }
            });
        });

        return detailsMap;
    }
}