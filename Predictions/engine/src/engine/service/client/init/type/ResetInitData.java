package engine.service.client.init.type;

import engine.EngineService;
import engine.service.client.init.SimulationInitRequest;
import engine.simulation.Simulation;
import engine.simulation.world.entity.Entity;
import engine.simulation.world.property.Property;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ResetInitData implements SimulationInitRequest {
    private EngineService engine;

    public ResetInitData() {
    }

    public ResetInitData(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];

        Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);
        optionalSimulation.ifPresent(simulation -> simulation.getWorld().getEntities()
                .forEach(Entity::resetPopulation));
        Map<String,String> resMap = new HashMap<>();

        if (optionalSimulation.isPresent()) {
            ArrayList<Property> environmentProperties = optionalSimulation.get().getWorld()
                    .getEnvironmentVariables().getProperties();

            try {
                for (Property environmentProperty : environmentProperties) {
                    environmentProperty.randomise();
                    resMap.put(environmentProperty.getPropertyName(), environmentProperty.getPropertyValue().toString());
                }
            } catch (InvalidPropertyType | ValueOutOfRange | InvalidPropertyReference
                     | IncompatibleTypes | InvalidEntity error) {
                return false;
            }
        }

        return resMap;
    }
}