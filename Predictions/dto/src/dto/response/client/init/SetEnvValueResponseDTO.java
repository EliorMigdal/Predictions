package dto.response.client.init;

import dto.DTO;

public class SetEnvValueResponseDTO implements DTO {
    private final Boolean successfullySet;
    private final String message;

    public SetEnvValueResponseDTO(Boolean successfullySet, String message) {
        this.successfullySet = successfullySet;
        this.message = message;
    }

    public Boolean getSuccessfullySet() {
        return successfullySet;
    }

    public String getMessage() {
        return message;
    }
}
