package engine.service.admin.management.type;

import dto.request.admin.management.LoadFileRequestDTO;
import dto.response.admin.management.LoadFileResponseDTO;
import engine.EngineService;
import engine.exception.SimulationAlreadyExists;
import engine.jaxb.generated.PRDWorld;
import engine.service.admin.management.ManagementRequest;
import engine.service.exception.InitException;
import engine.simulation.exception.creation.EntityCreationException;
import engine.simulation.exception.creation.EnvironmentCreationException;
import engine.simulation.exception.creation.RuleCreationException;
import engine.verifier.exception.*;
import engine.verifier.type.XMLChecker;
import engine.verifier.type.exception.XMLException;
import engine.verifier.type.grid.exception.InvalidGrid;

import javax.xml.bind.JAXBException;

public class LoadSimulation implements ManagementRequest {
    private EngineService engine;

    public LoadSimulation() {
    }

    public LoadSimulation(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args)
            throws XMLException, InitException {
        if (args[0] instanceof LoadFileRequestDTO) {
            LoadFileRequestDTO casted = (LoadFileRequestDTO) args[0];
            XMLChecker fileChecker = new XMLChecker();
            try {
                PRDWorld newSimulation = fileChecker.performChecks(casted.getFilePath());
                engine.loadSimulationDetails(newSimulation);
                assert newSimulation != null;
                return new LoadFileResponseDTO(true, "Successfully loaded " + newSimulation.getName() + "!");
            } catch (EnvPropertyDuplicationXML | EntityPropertyDuplicationXML | EntityReferenceError | FileIsNotXML |
                     FileDoesNotExist | JAXBException | InvalidGrid | RuleException | EnvironmentCreationException |
                     EntityCreationException | RuleCreationException | SimulationAlreadyExists exception) {
                return new LoadFileResponseDTO(false, exception.getMessage());
            }
        } else {
            return new LoadFileResponseDTO(false, "Invalid parameter!");
        }
    }
}
