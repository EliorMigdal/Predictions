package engine.services.mutual.simulations;

import dto.request.mutual.simulations.SimulationsListRequestDTO;
import dto.response.mutual.simulations.SimulationInfoDTO;
import dto.response.mutual.simulations.SimulationsListResponseDTO;
import engine.Engine;
import engine.services.Service;

public class SimulationsListService implements Service {
    public SimulationsListResponseDTO provideService(SimulationsListRequestDTO requestDTO, Engine engine) {
        SimulationsListResponseDTO responseDTO = new SimulationsListResponseDTO();

        engine.getVersionManager().getSimulationsVersions().getSimulationsVersions().keySet()
                .stream().filter(version -> version > requestDTO.getVersion())
                .forEach(match -> {
                    responseDTO.addNewSimulation(new SimulationInfoDTO(
                            engine.getVersionManager().getSimulationsVersions()
                                    .getSimulationsVersions().get(match).getKey().getName(),
                            engine.getVersionManager().getSimulationsVersions()
                                    .getSimulationsVersions().get(match).getValue()
                    ));

                    responseDTO.setVersion(match);
                });

        return responseDTO;
    }
}
