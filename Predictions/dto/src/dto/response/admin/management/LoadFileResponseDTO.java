package dto.response.admin.management;

import dto.DTO;

public class LoadFileResponseDTO implements DTO {
    private final boolean loadedSuccessfully;
    private final String message;

    public LoadFileResponseDTO(boolean loadedSuccessfully, String message) {
        this.loadedSuccessfully = loadedSuccessfully;
        this.message = message;
    }

    public boolean isLoadedSuccessfully() {
        return loadedSuccessfully;
    }

    public String getMessage() {
        return message;
    }
}
