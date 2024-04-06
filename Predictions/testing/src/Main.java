import dto.request.admin.management.LoadFileRequestDTO;
import engine.Engine;
import engine.services.admin.management.LoadXMLService;
import engine.simulation.exception.creation.EntityCreationException;
import engine.simulation.exception.creation.EnvironmentCreationException;
import engine.simulation.exception.creation.RuleCreationException;
import engine.simulation.exception.runtime.SimulationRuntimeException;

public class Main {
    public static void main(String[] args) throws EnvironmentCreationException, SimulationRuntimeException, EntityCreationException, RuleCreationException {
        Engine engine = new Engine();
        new LoadXMLService().provideService(
                new LoadFileRequestDTO("/Users/elior/Library/Mobile Documents/" +
                        "com~apple~CloudDocs/Studies/Java/Predictions/Web/Java-Project/" +
                        "Predictions/xml files/ex3-virus.xml"), engine);
        engine.runSimulationForTestings();
    }
}