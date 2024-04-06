package dto.request.client;

import dto.DTO;

public class InitialEnvironmentDTO implements DTO {
    private final String environmentName;
    private final String environmentType;
    private final String environmentRange;

    public InitialEnvironmentDTO(String environmentName, String environmentType, String environmentRange) {
        this.environmentName = environmentName;
        this.environmentType = environmentType;
        this.environmentRange = environmentRange;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public String getEnvironmentType() {
        return environmentType;
    }

    public String getEnvironmentRange() {
        return environmentRange;
    }
}
