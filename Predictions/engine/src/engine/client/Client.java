package engine.client;

import engine.client.request.Request;
import engine.client.usage.ClientUsageData;
import engine.jaxb.generated.PRDWorld;
import engine.simulation.Simulation;
import engine.simulation.exception.creation.EntityCreationException;
import engine.simulation.exception.creation.EnvironmentCreationException;
import engine.simulation.exception.creation.RuleCreationException;

import java.util.ArrayList;
import java.util.Collection;

public class Client {
    private final String clientName;
    private final Collection<Request> clientRequests = new ArrayList<>();
    private final ClientUsageData usageData = new ClientUsageData();

    private Simulation currentlyInitialized = null;
    private Integer nextAvailableID = 1;

    public Client(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public Collection<Request> getClientRequests() {
        return clientRequests;
    }

    public ClientUsageData getUsageData() {
        return usageData;
    }

    public void addNewRequest(Request requestData) {
        clientRequests.add(requestData);
    }

    public Integer createNewSimulation(PRDWorld simulation, Integer requestID) {
        if (currentlyInitialized == null) {
            final Integer[] returnValue = {-1};
            clientRequests.stream().filter(request -> request.getRequestID().equals(requestID))
                    .findFirst().ifPresent(request -> {
                        try {
                            Simulation newSim = request.addNewSimulation(simulation, nextAvailableID);
                            returnValue[0] = nextAvailableID;
                            currentlyInitialized = newSim;
                        } catch (EnvironmentCreationException | EntityCreationException | RuleCreationException exception) {
                            throw new RuntimeException(exception);
                        } finally {
                            nextAvailableID++;
                        }
                    });
            return returnValue[0];
        } else {
            return currentlyInitialized.getSimulationID();
        }
    }

    public void nullifyCurrentlyInitialized() {
        this.currentlyInitialized = null;
    }
}
