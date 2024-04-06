package engine.service.mutual.details.propertycard.type;

import engine.EngineService;
import engine.service.mutual.details.propertycard.PropertyCardRequest;

import java.util.LinkedHashMap;
import java.util.Map;

public class EnvironmentPropertyRequest implements PropertyCardRequest {
    private EngineService engine;

    public EnvironmentPropertyRequest() {
    }

    public EnvironmentPropertyRequest(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String simulationName = (String) args[0];
        String environmentName = (String) args[1];
        Map<String, String> propertyMap = new LinkedHashMap<>();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(simulationName))
                .findFirst().flatMap(simulation -> simulation.getPRDEnvironment().getPRDEnvProperty()
                        .stream().filter(envProp -> envProp.getPRDName().equals(environmentName))
                        .findFirst()).ifPresent(envProp -> {
                    propertyMap.put("Property type", envProp.getType());

                    if (envProp.getPRDRange() != null) {
                        propertyMap.put("Property range", envProp.getPRDRange().getFrom() + " to "
                                + envProp.getPRDRange().getTo());
                    }
                });

        return propertyMap;
    }
}