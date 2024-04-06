package dto.response.mutual.details.tree;

import dto.DTO;

import java.util.ArrayList;

public class TreeDetailsResponseDTO implements DTO {
    private final ArrayList<EntityDetailsDTO> entityDetails = new ArrayList<>();
    private final ArrayList<RulesDetailsDTO> rulesDetails = new ArrayList<>();
    private final ArrayList<EnvironmentDetailsDTO> environmentDetails = new ArrayList<>();

    public ArrayList<EntityDetailsDTO> getEntityDetails() {
        return entityDetails;
    }

    public ArrayList<RulesDetailsDTO> getRulesDetails() {
        return rulesDetails;
    }

    public ArrayList<EnvironmentDetailsDTO> getEnvironmentDetails() {
        return environmentDetails;
    }

    public void addEntity(EntityDetailsDTO entity) {
        entityDetails.add(entity);
    }

    public void addRule(RulesDetailsDTO rule) {
        rulesDetails.add(rule);
    }

    public void addEnvironmentProperty(EnvironmentDetailsDTO environmentProperty) {
        environmentDetails.add(environmentProperty);
    }
}
