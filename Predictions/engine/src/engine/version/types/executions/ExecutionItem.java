package engine.version.types.executions;

public class ExecutionItem {
    private final String clientName;
    private final Integer requestID;
    private final String simulationName;
    private final Integer simulationID;
    private Boolean isRunning = false;

    public ExecutionItem(String clientName, Integer requestID, String simulationName,
                         Integer simulationID, Boolean isRunning) {
        this.clientName = clientName;
        this.requestID = requestID;
        this.simulationName = simulationName;
        this.simulationID = simulationID;
        this.isRunning = isRunning;
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

    public Boolean getRunning() {
        return isRunning;
    }

    public void setRunning(Boolean running) {
        isRunning = running;
    }
}
