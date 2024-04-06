package dto.response.admin.activity;

import dto.DTO;

public class SetThreadSizeResponseDTO implements DTO {
    private final String message;
    private final boolean successfullySet;

    public SetThreadSizeResponseDTO(String message, boolean successfullySet) {
        this.message = message;
        this.successfullySet = successfullySet;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccessfullySet() {
        return successfullySet;
    }
}
