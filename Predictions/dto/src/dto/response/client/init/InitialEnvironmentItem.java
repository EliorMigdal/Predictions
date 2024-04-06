package dto.response.client.init;

import dto.DTO;

public class InitialEnvironmentItem implements DTO {
    private final String propertyName;
    private final String propertyValue;

    public InitialEnvironmentItem(String propertyName, String propertyValue) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }
}
