package engine.simulation.termination;

import engine.simulation.Simulation;

import java.io.Serializable;

public interface Termination extends Serializable {
    boolean checkForTermination(Simulation simulation);
    String getTerminationName();
    Object getTerminationTerm();
}