package dto.response.client.init;

import dto.DTO;

import java.util.ArrayList;

public class InitialEnvironmentsResponseDTO implements DTO {
    private final ArrayList<InitialEnvironmentItem> environments = new ArrayList<>();

    public ArrayList<InitialEnvironmentItem> getEnvironments() {
        return environments;
    }

    public void addNewItem(InitialEnvironmentItem newItem) {
        environments.add(newItem);
    }
}
