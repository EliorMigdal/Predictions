package dto.response.mutual.general;

import dto.DTO;

public class ErrorResponseDTO implements DTO {
    private final String errorMessage;

    public ErrorResponseDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
