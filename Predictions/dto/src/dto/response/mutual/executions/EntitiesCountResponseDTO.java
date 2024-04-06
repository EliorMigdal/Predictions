package dto.response.mutual.executions;

import java.util.ArrayList;

public class EntitiesCountResponseDTO {
    private final ArrayList<EntityCountDTO> entitiesCount = new ArrayList<>();

    public ArrayList<EntityCountDTO> getEntitiesCount() {
        return entitiesCount;
    }

    public void addNewEntityCount(EntityCountDTO newEntityCount) {
        entitiesCount.add(newEntityCount);
    }
}
