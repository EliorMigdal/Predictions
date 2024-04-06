package engine.client.usage;

public class ClientUsageData {
    private Integer numOfRunning = 0;
    private Integer numOfWaiting = 0;
    private Integer numOfFinished = 0;

    public Integer getNumOfRunning() {
        return numOfRunning;
    }

    public Integer getNumOfWaiting() {
        return numOfWaiting;
    }

    public Integer getNumOfFinished() {
        return numOfFinished;
    }

    public void incrementNumOfRunning() {
        numOfRunning++;
    }

    public void incrementNumOfWaiting() {
        numOfWaiting++;
    }

    public void incrementNumOfFinished() {
        numOfFinished++;
    }

    public void decrementNumOfRunning() {
        numOfRunning--;
    }

    public void decrementNumOfWaiting() {
        numOfWaiting--;
    }
}
