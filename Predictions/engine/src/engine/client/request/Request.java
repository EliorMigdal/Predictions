package engine.client.request;

import dto.request.client.requests.TerminationInfoDTO;
import engine.jaxb.generated.PRDWorld;
import engine.simulation.Simulation;
import engine.simulation.exception.creation.EntityCreationException;
import engine.simulation.exception.creation.EnvironmentCreationException;
import engine.simulation.exception.creation.RuleCreationException;
import engine.simulation.termination.Termination;
import engine.simulation.termination.exception.InvalidSecondsValue;
import engine.simulation.termination.type.ByTicks;
import engine.simulation.termination.type.ByTime;
import engine.simulation.world.rule.activation.exception.InvalidTicksValue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class Request {
    private final String clientName;
    private final Integer requestID;
    private final Collection<Simulation> simulations = new ArrayList<>();
    private final Integer numOfExecutions;
    private final Collection<Termination> terminations = new ArrayList<>();
    private final String simulationName;
    private Integer remainingExecutions;
    private final LocalDate requestDate;
    private String reqStatus = "Waiting";

    public Request(String clientName, Integer requestID, String numOfExecutions, String simulationName,
                   ArrayList<TerminationInfoDTO> terminations) {
        this.clientName = clientName;
        this.requestID = requestID;
        this.numOfExecutions = Integer.valueOf(numOfExecutions);
        this.remainingExecutions = Integer.valueOf(numOfExecutions);
        this.simulationName = simulationName;
        this.requestDate = LocalDate.now();
        terminations.forEach(term -> {
            try {
                if (term.getTermName().equals("By Ticks")) {
                    this.terminations.add(new ByTicks(Integer.parseInt(term.getTerminationTerm())));
                } else if (term.getTermName().equals("By Time")) {
                    this.terminations.add(new ByTime(Integer.parseInt(term.getTerminationTerm())));
                }
            } catch (InvalidSecondsValue | InvalidTicksValue exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    public String getReqStatus() {
        return reqStatus;
    }

    public Integer getRequestID() {
        return requestID;
    }

    public void setApprovalStatus(Boolean approved) {
        reqStatus = approved ? "Approved" : "Declined";
    }

    public Simulation getSimulationSample() { return this.simulations.iterator().next();}

    public Collection<Simulation> getSimulations() {
        return simulations;
    }

    public Integer getNumOfExecutions() {
        return numOfExecutions;
    }

    public String getTotal_RemainingAmount() {
        return numOfExecutions + "/" + remainingExecutions;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void decrementRemainingExecutions() {
        remainingExecutions--;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public ArrayList<TerminationInfoDTO> getTerminationInfo() {
            ArrayList<TerminationInfoDTO> terminationInfoDTOArrayList = new ArrayList<>();
        if (terminations.isEmpty()) {
            terminationInfoDTOArrayList.add(new TerminationInfoDTO("By User","User"));
        } else {
            terminations.forEach(termination -> terminationInfoDTOArrayList.add(new TerminationInfoDTO(termination.getTerminationName(),
                    termination.getTerminationTerm().toString())));
        }
        return terminationInfoDTOArrayList;
    }

    public String getClientName() {
        return clientName;
    }

    public Simulation addNewSimulation(PRDWorld simulation, Integer simulationID) throws EnvironmentCreationException,
            EntityCreationException, RuleCreationException {
        Simulation newSimulation = new Simulation(simulationID, simulation);
        newSimulation.setSimulationTerminations(terminations);
        this.simulations.add(newSimulation);
        return newSimulation;
    }

    public Integer getRemainingExecutions() {
        return remainingExecutions;
    }
}
