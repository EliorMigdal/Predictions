package engine.services.admin.executions;

import dto.request.admin.executions.ExecutedSimulationsRequestDTO;
import dto.response.admin.execution.ExecutedSimulationDTO;
import dto.response.admin.execution.ExecutedSimulationsResponseDTO;
import engine.Engine;
import engine.services.Service;
import engine.version.types.executions.ExecutionItem;

public class ExecutedSimulationsService implements Service {
    public ExecutedSimulationsResponseDTO getExecutedSimulations(ExecutedSimulationsRequestDTO requestDTO, Engine engine) {
        ExecutedSimulationsResponseDTO responseDTO = new ExecutedSimulationsResponseDTO();

        engine.getVersionManager().getExecutionsVersions().getExecutionVersions().keySet().stream()
                .filter(version -> version > requestDTO.getVersion())
                .forEach(match -> {
                    ExecutionItem matchedItem = engine.getVersionManager().getExecutionsVersions()
                            .getExecutionVersions().get(match);
                    responseDTO.addExecutedItem(new ExecutedSimulationDTO(
                            matchedItem.getClientName(), matchedItem.getRequestID(),
                            matchedItem.getSimulationName(), matchedItem.getSimulationID()
                    ));
                    responseDTO.setVersion(match);
                });

        return responseDTO;
    }
}
