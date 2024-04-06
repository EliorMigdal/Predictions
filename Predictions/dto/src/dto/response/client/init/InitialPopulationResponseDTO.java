package dto.response.client.init;

import dto.DTO;

import java.util.ArrayList;

public class InitialPopulationResponseDTO implements DTO {
    private final ArrayList<InitialPopulationItemDTO> populations = new ArrayList<>();

    public ArrayList<InitialPopulationItemDTO> getPopulations() {
        return populations;
    }

    public void addPopulationItem(InitialPopulationItemDTO initialDTO) {
        populations.add(initialDTO);
    }
}
