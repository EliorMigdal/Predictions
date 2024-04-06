package engine.service.client.user.type;

import engine.EngineService;
import engine.service.client.user.UserRequest;
import engine.service.exception.InitException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetSimulationNamesList implements UserRequest {
    private EngineService engine;

    public GetSimulationNamesList() {
    }

    public GetSimulationNamesList(EngineService engine) {
        this.engine = engine;
    }

    @Override
    public Object generateResponse(Object... args) throws InitException {
        ArrayList<String> simulationsList = new ArrayList<>();

        engine.getSimulationsCollection().keySet().forEach(simulation ->
                simulationsList.add( simulation.getName()));

        return simulationsList;
    }
}
