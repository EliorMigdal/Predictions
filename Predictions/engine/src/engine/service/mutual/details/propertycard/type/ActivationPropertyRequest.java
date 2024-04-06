package engine.service.mutual.details.propertycard.type;

import engine.EngineService;
import engine.service.mutual.details.propertycard.PropertyCardRequest;

import java.util.HashMap;
import java.util.Map;

public class ActivationPropertyRequest implements PropertyCardRequest {
    private EngineService engine;

    public ActivationPropertyRequest() {
    }

    public ActivationPropertyRequest(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        String simulationName = (String) args[0];
        String ruleName = (String) args[1];
        String activationName = (String) args[2];
        Map<String, String> activationDetails = new HashMap<>();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(simulationName))
                .findFirst().flatMap(simulation -> simulation.getPRDRules().getPRDRule()
                        .stream().filter(rule -> rule.getName().equals(ruleName))
                        .findFirst()).ifPresent(rule -> {
                            if (rule.getPRDActivation() != null) {
                                if (activationName.equalsIgnoreCase("ticks")) {
                                    if (rule.getPRDActivation().getTicks() != null) {
                                        activationDetails.put("Ticks value",
                                                rule.getPRDActivation().getTicks().toString());
                                    } else {
                                        activationDetails.put("Probability value", "1");
                                    }
                                } else {
                                    if (rule.getPRDActivation().getProbability() != null) {
                                        activationDetails.put("Ticks value",
                                                rule.getPRDActivation().getProbability().toString());
                                    } else {
                                        activationDetails.put("Probability value", "1.0");
                                    }
                                }
                            } else {
                                if (activationName.equalsIgnoreCase("ticks")) {
                                    activationDetails.put("Ticks value", "1");
                                } else {
                                    activationDetails.put("Probability value", "1.0");
                                }
                            }
                });

        return activationDetails;
    }
}