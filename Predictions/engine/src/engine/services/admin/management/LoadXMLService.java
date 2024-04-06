package engine.services.admin.management;

import dto.request.admin.management.LoadFileRequestDTO;
import dto.response.admin.management.LoadFileResponseDTO;
import engine.Engine;
import engine.exception.SimulationAlreadyExists;
import engine.jaxb.generated.PRDWorld;
import engine.services.Service;
import engine.verifier.exception.*;
import engine.verifier.type.XMLChecker;
import engine.verifier.type.grid.exception.InvalidGrid;

import javax.xml.bind.JAXBException;
import java.time.LocalDate;

public class LoadXMLService implements Service {
    public LoadFileResponseDTO provideService(LoadFileRequestDTO requestDTO, Engine engine) {
        try {
            XMLChecker fileChecker = new XMLChecker();
            PRDWorld newSimulation = fileChecker.performChecks(requestDTO.getFilePath());
            assert newSimulation != null;
            engine.loadSimulationDetails(newSimulation);
            engine.getVersionManager().addNewSimulation(newSimulation, LocalDate.now());
            return new LoadFileResponseDTO(true, "Successfully loaded " +
                    newSimulation.getName() + "!");
        } catch (EntityPropertyDuplicationXML | FileIsNotXML | EntityReferenceError | FileDoesNotExist |
                 JAXBException | EnvPropertyDuplicationXML | RuleException | InvalidGrid
                 | SimulationAlreadyExists exception) {
            return new LoadFileResponseDTO(false, exception.getMessage());
        }
    }
}
