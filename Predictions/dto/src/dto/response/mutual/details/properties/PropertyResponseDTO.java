package dto.response.mutual.details.properties;

import dto.DTO;

import java.util.ArrayList;

public class PropertyResponseDTO implements DTO {
    private final ArrayList<PropertyDTO> properties = new ArrayList<>();

    public ArrayList<PropertyDTO> getProperties() {
        return properties;
    }

    public void addProperty(PropertyDTO property) {
        properties.add(property);
    }
}
