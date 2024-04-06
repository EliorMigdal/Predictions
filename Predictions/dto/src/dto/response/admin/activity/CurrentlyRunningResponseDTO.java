package dto.response.admin.activity;

import dto.DTO;

import java.util.ArrayList;

public class CurrentlyRunningResponseDTO implements DTO {
    private final ArrayList<CurrentlyRunningDTO> currentlyRunning = new ArrayList<>();

    public ArrayList<CurrentlyRunningDTO> getCurrentlyRunning() {
        return currentlyRunning;
    }

    public void andNewRunningItem(CurrentlyRunningDTO runningDTO) {
        currentlyRunning.add(runningDTO);
    }
}
