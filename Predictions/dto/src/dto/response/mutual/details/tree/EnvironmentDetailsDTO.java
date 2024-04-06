package dto.response.mutual.details.tree;

public class EnvironmentDetailsDTO {
    private final String environmentPropertyName;

    public EnvironmentDetailsDTO(String environmentPropertyName) {
        this.environmentPropertyName = environmentPropertyName;
    }

    public String getEnvironmentPropertyName() {
        return environmentPropertyName;
    }
}
