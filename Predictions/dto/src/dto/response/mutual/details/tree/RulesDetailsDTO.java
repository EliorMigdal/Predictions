package dto.response.mutual.details.tree;

import dto.DTO;

import java.util.ArrayList;

public class RulesDetailsDTO implements DTO {
    private final String ruleName;
    private final ArrayList<ActionsDetailsDTO> actionDetails = new ArrayList<>();
    private final ArrayList<ActivationDetailsDTO> activationDetails = new ArrayList<>();

    public RulesDetailsDTO(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public ArrayList<ActionsDetailsDTO> getActionDetails() {
        return actionDetails;
    }

    public ArrayList<ActivationDetailsDTO> getActivationDetails() {
        return activationDetails;
    }

    public void addAction(ActionsDetailsDTO action) {
        actionDetails.add(action);
    }

    public void addActivationTerm(ActivationDetailsDTO activationDetailsDTO) {
        activationDetails.add(activationDetailsDTO);
    }
}
