package dto.response.mutual.details.properties;

import dto.DTO;

public class PropertyDTO implements DTO {
    private final String propertyTitle;
    private final String propertyValue;

    public PropertyDTO(String propertyTitle, String propertyValue) {
        this.propertyTitle = propertyTitle;
        this.propertyValue = propertyValue;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public String getPropertyValue() {
        return propertyValue;
    }
}
