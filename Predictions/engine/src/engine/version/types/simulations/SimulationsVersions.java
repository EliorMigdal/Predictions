package engine.version.types.simulations;

import engine.jaxb.generated.PRDWorld;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimulationsVersions {
    private final Map<Integer, Pair<PRDWorld, LocalDate>> simulationsVersions = new LinkedHashMap<>();
    private Integer latestVersion = 0;

    public void addNewSimulation(PRDWorld world, LocalDate date) {
        simulationsVersions.put(++latestVersion, new Pair<>(world, date));
    }

    public Map<Integer, Pair<PRDWorld, LocalDate>> getSimulationsVersions() {
        return simulationsVersions;
    }
}
