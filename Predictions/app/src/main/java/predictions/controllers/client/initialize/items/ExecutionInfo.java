package predictions.controllers.client.initialize.items;

import predictions.controllers.mutual.executions.items.EntityItem;

import java.util.ArrayList;

public class ExecutionInfo {
    private final String clientName;
    private final Integer requestID;
    private final String simulationName;
    private final Integer simulationID;
    private final ArrayList<EntityItem> entityItems = new ArrayList<>();

    public ExecutionInfo(String clientName, Integer requestID, String simulationName, Integer simulationID) {
        this.clientName = clientName;
        this.requestID = requestID;
        this.simulationName = simulationName;
        this.simulationID = simulationID;
    }

    public String getClientName() {
        return clientName;
    }

    public Integer getRequestID() {
        return requestID;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public Integer getSimulationID() {
        return simulationID;
    }

    public void addNewEntityItem(EntityItem item) {
        entityItems.add(item);
    }

    public ArrayList<EntityItem> getEntityItems() {
        return entityItems;
    }
}
