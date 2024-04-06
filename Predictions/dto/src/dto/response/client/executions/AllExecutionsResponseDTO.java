package dto.response.client.executions;

import dto.DTO;

import java.util.ArrayList;

public class AllExecutionsResponseDTO implements DTO {
    private final ArrayList<ExecutionDTO> executions = new ArrayList<>();

    public ArrayList<ExecutionDTO> getExecutions() {
        return executions;
    }

    public void addNewExecutionItem(ExecutionDTO executionDTO) {
        executions.add(executionDTO);
    }
}
