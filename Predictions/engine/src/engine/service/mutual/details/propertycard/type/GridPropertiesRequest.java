package engine.service.mutual.details.propertycard.type;

import engine.EngineService;
import engine.service.mutual.details.propertycard.PropertyCardRequest;

import java.util.HashMap;
import java.util.Map;

public class GridPropertiesRequest implements PropertyCardRequest {
    private EngineService engine;

    public GridPropertiesRequest() {
    }

    public GridPropertiesRequest(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String simulationName = (String) args[0];
        Map<String, String> gridMap = new HashMap<>();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(simulationName))
                .findFirst().ifPresent(simulation -> {
                    gridMap.put("Y-Axis", ((Integer) simulation.getPRDGrid().getRows()).toString());
                    gridMap.put("X-Axis", ((Integer) simulation.getPRDGrid().getColumns()).toString());
                });

        return gridMap;
    }
}
