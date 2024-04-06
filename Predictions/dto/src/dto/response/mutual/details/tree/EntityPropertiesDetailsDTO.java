package dto.response.mutual.details.tree;

public class EntityPropertiesDetailsDTO {
    private final String propertyName;

    public EntityPropertiesDetailsDTO(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
