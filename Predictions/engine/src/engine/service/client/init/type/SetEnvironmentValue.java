package engine.service.client.init.type;

import engine.EngineService;
import engine.service.client.init.SimulationInitRequest;
import engine.simulation.Simulation;
import engine.simulation.world.property.Property;
import engine.simulation.world.property.exception.InvalidPropertyType;
import engine.simulation.world.property.exception.ValueOutOfRange;
import engine.simulation.world.rule.action.condition.exception.IncompatibleTypes;
import engine.simulation.world.rule.action.expression.exception.InvalidEntity;
import engine.utility.function.Function;
import engine.utility.function.exception.InvalidPropertyReference;
import engine.utility.function.type.cast.GenerateBooleanFromString;
import engine.utility.function.type.cast.GenerateNumberFromString;

import java.util.Optional;

public class SetEnvironmentValue implements SimulationInitRequest {
    private EngineService engine;

    public SetEnvironmentValue() {
    }

    public SetEnvironmentValue(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String clientName = (String) args[0];
        Integer requestID = (Integer) args[1];
        Integer simulationID = (Integer) args[2];
        String environmentName = (String) args[3];
        Object environmentValue = args[4];

        Optional<Simulation> optionalSimulation = engine.getRequestSimulation(clientName, requestID, simulationID);

        optionalSimulation.ifPresent(simulation -> {
            Optional<Property> optionalEnvironment = simulation.getWorld().getEnvironmentVariables()
                    .getProperties().stream()
                    .filter(property -> property.getPropertyName().equals(environmentName))
                    .findFirst();

            optionalEnvironment.ifPresent(environment -> {
                try {
                    Function generateBool = new GenerateBooleanFromString((String) environmentValue);
                    Function generateNum = new GenerateNumberFromString((String) environmentValue);

                    Object boolRes = generateBool.run();
                    Object numRes = generateNum.run();

                    if (boolRes != null) {
                        environment.setPropertyValue(boolRes);
                    } else if (numRes != null) {
                        environment.setPropertyValue(numRes);
                    } else {
                        environment.setPropertyValue(environmentValue);
                    }
                } catch (InvalidPropertyType | ValueOutOfRange | InvalidPropertyReference | IncompatibleTypes |
                         InvalidEntity exception) {
                    throw new RuntimeException(exception);
                }
            });
        });

        return true;
    }
}
