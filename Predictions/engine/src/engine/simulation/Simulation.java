package engine.simulation;

import engine.jaxb.generated.PRDWorld;
import engine.simulation.details.Details;
import engine.simulation.exception.creation.EntityCreationException;
import engine.simulation.exception.creation.EnvironmentCreationException;
import engine.simulation.exception.creation.RuleCreationException;
import engine.simulation.exception.runtime.SimulationRuntimeException;
import engine.simulation.status.Status;
import engine.simulation.termination.Termination;
import engine.simulation.termination.type.ByTicks;
import engine.simulation.termination.type.ByTime;
import engine.simulation.time.Time;
import engine.simulation.time.type.PauseTime;
import engine.simulation.world.World;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public class Simulation implements Serializable {
    private World world = null;
    private final Details simulationDetails;
    private final Status simulationStatus;
    private final Time simulationTime;

    public Simulation(int simulationID, PRDWorld prdWorld)
            throws EnvironmentCreationException, EntityCreationException, RuleCreationException {
        this.simulationDetails = new Details(simulationID);
        this.simulationStatus = new Status();
        this.simulationTime = new Time();
        this.loadSimulationDetails(prdWorld);
    }

    public Integer getSimulationID() {
        return simulationDetails.getSimulationID();
    }

    public Integer getCurrentTick() {
        return this.simulationStatus.getCurrentTick();
    }

    public World getWorld() {
        return world;
    }

    public Collection<Termination> getTerminationsTerms() {
        return simulationDetails.getTerminations();
    }

    public void loadSimulationDetails(PRDWorld world)
            throws EnvironmentCreationException, EntityCreationException, RuleCreationException {
        if (world.getSleep() != null) {
            simulationDetails.setSleepTime(world.getSleep());
        }

        simulationDetails.setSimulationName(world.getName());
        this.world = new World(world);
    }

    public long getStartTime() {
        return simulationTime.getSimulationRunTime().getStartTime();
    }

    public void setStartTime(long startTime) {
        simulationTime.getSimulationRunTime().setStartTime(startTime);
    }

    public void runSimulation() throws SimulationRuntimeException {
        setStartTime(System.currentTimeMillis());
        boolean terminate = false;
        do {
            try {
                Thread.sleep(simulationDetails.getSleepTime());
            } catch (InterruptedException exception) {
                throw new RuntimeException(exception);
            }

            if (this.simulationStatus.getPauseStatus()) {
                while (this.simulationStatus.getPauseStatus() && !terminate) {
                    simulationTime.getSimulationPauseTime().setPauseTime(System.currentTimeMillis());
                    if (this.simulationStatus.toCancelManualProgress()) {
                        this.simulationStatus.setCancelManualProgress(false);
                    } else if (this.simulationStatus.isManualProgressRequested()) {
                        this.simulationStatus.setManualProgressRequest(false); // Reset the flag

                        this.tick();
                        terminate = simulationDetails.getTerminations().stream()
                                .anyMatch(term -> term.checkForTermination(this));
                    }
                    simulationTime.getSimulationPauseTime().setCurrentPauseEndTime(System.currentTimeMillis());
                }
            }

            if (this.simulationStatus.getStopStatus()) {
                break;
            }

            this.tick();
            terminate = simulationDetails.getTerminations().stream()
                    .anyMatch(term -> term.checkForTermination(this));
        } while (!terminate);

        this.simulationStatus.setFinishStatus(true);
    }

    public boolean isManualProgressRequested() {
        return this.simulationStatus.isManualProgressRequested();
    }

    public void requestManualProgress() {
        this.simulationStatus.setManualProgressRequest(true);
        this.simulationStatus.setCancelManualProgress(false);
    }

    public void cancelManualProgress() {
        this.simulationStatus.setManualProgressRequest(false);
        this.simulationStatus.setCancelManualProgress(true);
    }

    public Integer getTimeProgress() {
        double timeElapsed;

        if (this.simulationStatus.getPauseStatus() || this.simulationStatus.getStopStatus()
                || this.simulationStatus.getFinishStatus()) {
            timeElapsed = simulationTime.getRunTime();
        } else {
            timeElapsed = simulationTime.getTimeProgress();
        }

        return (int) timeElapsed;
    }

    synchronized public void setPauseStatus(Boolean pauseStatus) {
        this.simulationStatus.setPauseStatus(pauseStatus);
    }

    synchronized public void setStopStatus(Boolean stopStatus) {
        this.simulationStatus.setStopStatus(stopStatus);
    }

    synchronized public Boolean getPauseStatus() {
        return this.simulationStatus.getPauseStatus();
    }

    synchronized public PauseTime getPauseTime() {
        return simulationTime.getSimulationPauseTime();
    }

    synchronized private void tick() throws SimulationRuntimeException {
        this.simulationStatus.incrementTick();
        try {
            this.world.tick(this.simulationStatus.getCurrentTick());
            this.simulationTime.updateHistory(this.simulationStatus.getCurrentTick(), world.clone());
        } catch (SimulationRuntimeException exception) {
            this.simulationStatus.setFinishStatus(true);
            this.simulationStatus.setStopReason(exception.getMessage());
            throw new SimulationRuntimeException(exception.getMessage());
        }
    }

    synchronized public void setFinishStatus(Boolean finishStatus) {
        simulationStatus.setFinishStatus(finishStatus);
    }

    synchronized public void setQueueStatus(Boolean queueStatus) {
        simulationStatus.setQueueStatus(queueStatus);
    }

    public Boolean hasStartedRunning() {
        return simulationStatus.hasStartedRunning();
    }

    public String getSimulationName() {
        return simulationDetails.getSimulationName();
    }

    synchronized public void setRunStatus(Boolean runStatus) {
        simulationStatus.setRunStatus(runStatus);
    }

    public Boolean getFinishStatus() {
        return simulationStatus.getFinishStatus();
    }

    public Boolean getQueueStatus() {
        return simulationStatus.getQueueStatus();
    }

    public String getSimulationError() {
        return simulationStatus.getStopReason();
    }

    public String getTicksPercentage() {
        Optional<Termination> ticksValue = simulationDetails.getTerminations().stream()
                .filter(termination -> termination.getTerminationName().equals("Ticks")).findFirst();

        if (ticksValue.isPresent()) {
            if (ticksValue.get() instanceof ByTicks) {
                ByTicks castedTerm = (ByTicks) ticksValue.get();

                if (castedTerm.getTerminationTerm() instanceof Integer) {
                    Integer castedValue = (Integer) castedTerm.getTerminationTerm();
                    Integer currentTick = simulationStatus.getCurrentTick();

                    return ((Double) (currentTick.doubleValue() / castedValue.doubleValue())).toString();
                } else {
                    return "0";
                }
            } else {
                return "0";
            }
        } else {
            return "0";
        }
    }

    public String getTimePercentage() {
        Optional<Termination> timeValue = simulationDetails.getTerminations().stream()
                .filter(termination -> termination.getTerminationName().equals("Time")).findFirst();

        if (timeValue.isPresent()) {
            if (timeValue.get() instanceof ByTime) {
                ByTime castedTerm = (ByTime) timeValue.get();

                if (castedTerm.getTerminationTerm() instanceof Integer) {
                    Integer castedValue = (Integer) castedTerm.getTerminationTerm();

                    return ((Double) (getTimeProgress() / castedValue.doubleValue())).toString();
                } else {
                    return "0";
                }
            } else {
                return "0";
            }
        } else {
            return "0";
        }
    }

    public void setSimulationTerminations(Collection<Termination> terminations) {
        simulationDetails.setTerminations(terminations);
    }
}