package dto.response.mutual.details.tree;

import dto.DTO;

public class ActivationDetailsDTO implements DTO {
    private final String activationName;

    public ActivationDetailsDTO(String activationName) {
        this.activationName = activationName;
    }

    public String getActivationName() {
        return activationName;
    }
}
