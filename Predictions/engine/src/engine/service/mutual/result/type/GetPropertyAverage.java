package engine.service.mutual.result.type;

import engine.EngineService;
import engine.service.mutual.result.ResultRequest;
import engine.simulation.Simulation;
import engine.simulation.world.entity.instance.Instance;

import java.util.Optional;

public class GetPropertyAverage implements ResultRequest {
    private EngineService engine;

    public GetPropertyAverage() {
    }

    public GetPropertyAverage(EngineService engine) {
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
                                .filter(Instance::isAlive)
                                .map(instance -> {
                                    Object value = instance.searchForProperty(propertyName).getPropertyValue();
                                    if (value instanceof Float) {
                                        return ((Float) value).doubleValue();
                                    } else if (value instanceof Integer) {
                                        return ((Integer) value).doubleValue();
                                    } else {
                                        return 0.0;
                                    }
                                })
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
