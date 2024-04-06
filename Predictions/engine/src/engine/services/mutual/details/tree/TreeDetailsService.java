package engine.services.mutual.details.tree;

import dto.request.mutual.details.tree.TreeDetailsRequestDTO;
import dto.response.mutual.details.tree.*;
import engine.Engine;
import engine.services.Service;

public class TreeDetailsService implements Service {
    public TreeDetailsResponseDTO provideService(TreeDetailsRequestDTO requestDTO, Engine engine) {
        TreeDetailsResponseDTO responseDTO = new TreeDetailsResponseDTO();

        engine.getSimulationsCollection().keySet().stream()
                .filter(simulation -> simulation.getName().equals(requestDTO.getSimulationName()))
                .findFirst().ifPresent(simulation -> {
                    simulation.getPRDEntities().getPRDEntity().forEach(entity -> {
                        EntityDetailsDTO newItem = new EntityDetailsDTO(entity.getName());

                        entity.getPRDProperties().getPRDProperty().forEach(property ->
                                newItem.addProperty(new EntityPropertiesDetailsDTO(property.getPRDName())));

                        responseDTO.addEntity(newItem);
                    });

                    simulation.getPRDRules().getPRDRule().forEach(rule -> {
                        RulesDetailsDTO newItem = new RulesDetailsDTO(rule.getName());

                        rule.getPRDActions().getPRDAction().forEach(action ->
                                newItem.addAction(new ActionsDetailsDTO(action.getType())));

                        newItem.addActivationTerm(new ActivationDetailsDTO("Probability"));
                        newItem.addActivationTerm(new ActivationDetailsDTO("Ticks"));
                        responseDTO.addRule(newItem);
                    });

                    simulation.getPRDEnvironment().getPRDEnvProperty().forEach(property ->
                            responseDTO.addEnvironmentProperty(new EnvironmentDetailsDTO(property.getPRDName())));
                });

        return responseDTO;
    }
}
