package engine.service.admin.management.type;

import engine.EngineService;
import engine.service.admin.management.ManagementRequest;
import engine.service.exception.InitException;
import engine.verifier.type.exception.XMLException;
import dto.request.mutual.executions.SimulationRuntimeInfoRequestDTO;
import dto.response.mutual.executions.PopulationResultsResponseDTO;
import dto.response.mutual.executions.PopulationDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GetPopulationHistory implements ManagementRequest {
    private EngineService engine;

    public GetPopulationHistory() {
    }

    public GetPopulationHistory(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) throws XMLException, InitException {
        return null;
    }
}
