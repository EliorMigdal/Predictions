package engine.service.client.init.type;

import engine.EngineService;
import engine.service.client.init.SimulationInitRequest;
import engine.service.exception.InitException;
import engine.simulation.Simulation;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.exception.InvalidPropertyReference;

import java.util.Optional;

public class RandomiseRestOfEnvironments implements SimulationInitRequest {
    private EngineService engine;

    public RandomiseRestOfEnvironments() {
    }

    public RandomiseRestOfEnvironments(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) throws InitException {
        try {
            String clientName = (String) args[0];
            Integer requestID = (Integer) args[1];
            Integer simulationID = (Integer) args[2];
            Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);

            if (optionalSimulation.isPresent()) {
                optionalSimulation.get().getWorld().getEnvironmentVariables().randomiseRest();
            }

            return true;
        } catch (InvalidPropertyType | InvalidPropertyReference | IncompatibleTypes
                 | ValueOutOfRange | InvalidEntity exception) {
            throw new InitException(exception.getMessage());
        }
    }
}