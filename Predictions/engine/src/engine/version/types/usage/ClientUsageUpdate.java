package engine.version.types.usage;

import engine.client.usage.ClientUsageData;

public class ClientUsageUpdate {
    private final String clientName;
    private final Integer numOfRunning;
    private final Integer numOfWaiting;
    private final Integer numOfFinished;

    public ClientUsageUpdate(String clientName, ClientUsageData usageData) {
        this.clientName = clientName;
        numOfRunning = usageData.getNumOfRunning();
        numOfWaiting = usageData.getNumOfWaiting();
        numOfFinished = usageData.getNumOfFinished();
    }

    public String getClientName() {
        return clientName;
    }

    public Integer getNumOfRunning() {
        return numOfRunning;
    }

    public Integer getNumOfWaiting() {
        return numOfWaiting;
    }

    public Integer getNumOfFinished() {
        return numOfFinished;
    }
}
