package engine.services.mutual.details.properties;

import dto.request.mutual.details.properties.ActionPropertyRequestDTO;
import dto.response.mutual.details.properties.PropertyDTO;
import dto.response.mutual.details.properties.PropertyResponseDTO;
import engine.Engine;
import engine.jaxb.generated.PRDAction;
import engine.services.Service;

import java.util.LinkedHashMap;
import java.util.Map;

public class ActionPropertyService implements Service {
    public PropertyResponseDTO provideService(ActionPropertyRequestDTO requestDTO, Engine engine) {
        PropertyResponseDTO responseDTO = new PropertyResponseDTO();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(requestDTO.getSimulationName()))
                .findFirst().flatMap(simulation -> simulation.getPRDRules().getPRDRule().stream()
                        .filter(rule -> rule.getName().equals(requestDTO.getRuleName()))
                        .findFirst()).ifPresent(rule -> {
                    PRDAction desiredAction = rule.getPRDActions().getPRDAction()
                            .get(Integer.parseInt(requestDTO.getActionIndex()));

                    if (desiredAction.getType().equalsIgnoreCase(requestDTO.getActionName())) {
                        responseDTO.addProperty(new PropertyDTO("Type:", desiredAction.getType()));

                        if (desiredAction.getType().equalsIgnoreCase("calculation")) {
                            if (desiredAction.getPRDDivide() != null) {
                                responseDTO.addProperty(new PropertyDTO("Operator:", "Divide"));
                            } else {
                                responseDTO.addProperty(new PropertyDTO("Operator:", "Multiply"));
                            }
                        }

                        if (desiredAction.getType().equalsIgnoreCase("proximity")) {
                            responseDTO.addProperty(new PropertyDTO("Source entity:",
                                    desiredAction.getPRDBetween().getSourceEntity()));
                        } else if (desiredAction.getType().equalsIgnoreCase("replace")) {
                            responseDTO.addProperty(new PropertyDTO("Replace:", desiredAction.getKill()));
                        } else {
                            responseDTO.addProperty(new PropertyDTO("Main entity:", desiredAction.getEntity()));
                        }

                        if (desiredAction.getPRDSecondaryEntity() != null) {
                            responseDTO.addProperty(new PropertyDTO("Secondary entity:",
                                    desiredAction.getPRDSecondaryEntity().getEntity()));
                        }

                        Map<String, String> argumentsMap = this.generateArgumentMap(desiredAction,
                                desiredAction.getType());

                        argumentsMap.keySet().forEach(key ->
                                responseDTO.addProperty(new PropertyDTO(key, argumentsMap.get(key))));
                    }
                });

        return responseDTO;
    }

    private Map<String,String> generateArgumentMap(PRDAction action, String actionType) {
        Map<String,String> argumentsMap = new LinkedHashMap<>();

        if (actionType.equalsIgnoreCase("calculation")) {
            if (action.getPRDDivide() != null) {
                argumentsMap.put("Argument#1:", action.getPRDDivide().getArg1());
                argumentsMap.put("Argument#2:", action.getPRDDivide().getArg2());
            } else {
                argumentsMap.put("Argument#1:", action.getPRDMultiply().getArg1());
                argumentsMap.put("Argument#2:", action.getPRDMultiply().getArg2());
            }

            argumentsMap.put("Result prop:", action.getResultProp());
        } else if (actionType.equalsIgnoreCase("condition")) {
            if (action.getPRDCondition().getSingularity().equalsIgnoreCase("multiple")) {
                argumentsMap.put("Condition type:", "Multiple");
                argumentsMap.put("Logical operation:", action.getPRDCondition().getLogical());
                argumentsMap.put("Num of conditions:",
                        ((Integer) action.getPRDCondition().getPRDCondition().size()).toString());
            } else {
                argumentsMap.put("Condition type:", "Singular");
                argumentsMap.put("Condition:", action.getPRDCondition().getProperty() + " " +
                        action.getPRDCondition().getOperator() + " " + action.getPRDCondition().getValue());
            }

            if (action.getPRDThen() != null) {
                argumentsMap.put("'Then' actions:",
                        ((Integer) action.getPRDThen().getPRDAction().size()).toString());
            } else {
                argumentsMap.put("'Then' actions:", "0");
            }

            if (action.getPRDElse() != null) {
                argumentsMap.put("'Else' actions:", ((Integer) action.getPRDElse().getPRDAction().size()).toString());
            } else {
                argumentsMap.put("'Else' actions:", "0");
            }
        } else if (actionType.equalsIgnoreCase("kill")) {
            argumentsMap.put("Kill:", action.getEntity());
        } else if (actionType.equalsIgnoreCase("decrease")
                || actionType.equalsIgnoreCase("increase")) {
            argumentsMap.put("Property:", action.getProperty());
            argumentsMap.put("By:", action.getBy());
        } else if (actionType.equalsIgnoreCase("set")) {
            argumentsMap.put("Property:", action.getProperty());
            argumentsMap.put("To:", action.getValue());
        } else if (actionType.equalsIgnoreCase("proximity")) {
            argumentsMap.put("Target entity:", action.getPRDBetween().getTargetEntity());
            argumentsMap.put("Circular distance:", action.getPRDEnvDepth().getOf());
            argumentsMap.put("Num of actions:", ((Integer) action.getPRDActions().getPRDAction().size()).toString());
        } else if (actionType.equalsIgnoreCase("replace")) {
            argumentsMap.put("With:", action.getCreate());
            argumentsMap.put("Mode:", action.getMode());
        }

        return argumentsMap;
    }
}
