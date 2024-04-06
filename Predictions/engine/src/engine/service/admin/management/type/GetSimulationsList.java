package engine.service.admin.management.type;

import engine.EngineService;
import engine.service.admin.management.ManagementRequest;
import dto.response.mutual.simulations.SimulationInfoDTO;

import java.util.stream.Collectors;

public class GetSimulationsList implements ManagementRequest {
    private EngineService engine;

    public GetSimulationsList() {
    }

    public GetSimulationsList(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) {
        return null;
    }
}
