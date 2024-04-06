package dto.request.admin.management;

import dto.DTO;

public class LoadFileRequestDTO implements DTO {
    private final String filePath;

    public LoadFileRequestDTO(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
