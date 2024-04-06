package dto.response.client.init;

import dto.DTO;

import java.util.ArrayList;

public class AllEnvPropertiesDTO implements DTO {
    private final ArrayList<EnvPropertyDTO> environments = new ArrayList<>();

    public ArrayList<EnvPropertyDTO> getEnvironments() {
        return environments;
    }

    public void addNewItem(EnvPropertyDTO newItem) {
        environments.add(newItem);
    }
}
