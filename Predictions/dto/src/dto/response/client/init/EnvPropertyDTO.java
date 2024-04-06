package dto.response.client.init;

import dto.DTO;

public class EnvPropertyDTO implements DTO {
    private final String propertyName;
    private final String propertyType;
    private String propertyRange = "";

    public EnvPropertyDTO(String propertyName, String propertyType) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getPropertyRange() {
        return propertyRange;
    }

    public void setPropertyRange(String propertyRange) {
        this.propertyRange = propertyRange;
    }
}
