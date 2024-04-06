package engine.simulation.details;

import engine.simulation.termination.Termination;

import java.util.ArrayList;
import java.util.Collection;

public class Details {
    private String simulationName;
    private final Integer simulationID;
    private Integer sleepTime = 300;
    private Collection<Termination> terminations = new ArrayList<>();

    public Details(Integer simulationID) {
        this.simulationID = simulationID;
    }

    public Integer getSimulationID() {
        return simulationID;
    }

    public Collection<Termination> getTerminations() {
        return terminations;
    }

    public Integer getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Integer sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public void setSimulationName(String simulationName) {
        this.simulationName = simulationName;
    }

    public void setTerminations(Collection<Termination> terminations) {
        this.terminations = terminations;
    }
}
