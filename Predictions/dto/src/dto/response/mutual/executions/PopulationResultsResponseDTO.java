package dto.response.mutual.executions;

import dto.DTO;

import java.util.ArrayList;

public class PopulationResultsResponseDTO implements DTO {
    private final ArrayList<EntityPopulationDTO> entityPopulations = new ArrayList<>();

    public ArrayList<EntityPopulationDTO> getEntityPopulations() {
        return entityPopulations;
    }

    public void addNewPopulationItem(EntityPopulationDTO newItem) {
        entityPopulations.add(newItem);
    }
}
