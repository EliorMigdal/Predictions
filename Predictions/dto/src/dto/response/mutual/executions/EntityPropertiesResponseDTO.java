package dto.response.mutual.executions;

import dto.DTO;

import java.util.ArrayList;

public class EntityPropertiesResponseDTO implements DTO {
    private final ArrayList<String> propertyNames = new ArrayList<>();

    public ArrayList<String> getPropertyNames() {
        return propertyNames;
    }

    public void addPropertyName(String propertyName) {
        propertyNames.add(propertyName);
    }
}
