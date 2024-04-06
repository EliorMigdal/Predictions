package engine.service.mutual.result.type;

import engine.EngineService;
import engine.service.mutual.result.ResultRequest;
import engine.simulation.Simulation;

import java.util.Optional;

public class GetPropertyConsistency implements ResultRequest {
    private EngineService engine;

    public GetPropertyConsistency() {
    }

    public GetPropertyConsistency(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];
        String entityName = (String) args[3];
        String propertyName = (String) args[4];

        Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);

        double averages = optionalSimulation.flatMap(simulation ->
                simulation.getWorld().getEntities().stream()
                        .filter(entity -> entity.getEntityName().equals(entityName))
                        .findFirst()
                        .map(entity -> entity.getEntityInstances().stream()
                                .map(instance -> instance.searchForProperty(propertyName).calculateConsistency())
                                .reduce(0.0, Double::sum))
        ).orElse(0.0);

        int size = optionalSimulation.flatMap(simulation -> simulation.getWorld().getEntities()
                        .stream()
                        .filter(entity -> entity.getEntityName().equals(entityName))
                        .map(entity -> entity.getEntityInstances().size())
                        .findFirst())
                .orElse(1);

        String formattedAverage = String.format("%.3f", averages / size);
        return Double.parseDouble(formattedAverage);
    }
}
